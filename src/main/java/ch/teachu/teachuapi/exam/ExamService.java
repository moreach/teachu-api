package ch.teachu.teachuapi.exam;

import ch.teachu.teachuapi.daos.AuthDAO;
import ch.teachu.teachuapi.dtos.MessageResponse;
import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.errorhandling.InvalidException;
import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.exam.dto.*;
import ch.teachu.teachuapi.parent.AbstractService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static ch.teachu.teachuapi.generated.tables.Exam.EXAM;

@Service
public class ExamService extends AbstractService {

    private final ExamRepo examRepo;

    public ExamService(ExamRepo examRepo) {
        this.examRepo = examRepo;
    }

    public ResponseEntity<SemestersExamsResponse> loadExams(String auth) {
        AuthDAO authDAO = authMinRole(auth, UserRole.STUDENT);
        List<SemesterExamsResponse> semesters = loadExamsWithoutAverages(authDAO);
        calculateAverages(semesters);
        return ResponseEntity.ok(new SemestersExamsResponse(semesters));
    }

    public void calculateAverages(List<SemesterExamsResponse> semesters) {
        for (SemesterExamsResponse semester : semesters) {
            for (SchoolClassExamsResponse schoolClass : semester.getSchoolClasses()) {
                calculateAverages(schoolClass);
            }
        }
    }

    private void calculateAverages(SchoolClassExamsResponse schoolClass) {
        double sum = 0;
        double weight = 0;
        for (SubjectExamsResponse subject : schoolClass.getSubjects()) {
            calculateAverages(subject);
            sum += subject.getAverageMark() * subject.getWeight();
            weight += subject.getWeight();
        }
        schoolClass.setAverageMark(sum / weight);
    }

    private void calculateAverages(SubjectExamsResponse subject) {
        double sum = 0;
        double weight = 0;
        for (ExamResponse exam : subject.getExams()) {
            calculateAverages(exam);
            sum += exam.getAverageMark() * exam.getWeight();
            weight += exam.getWeight();
        }
        subject.setAverageMark(sum / weight);
    }

    private void calculateAverages(ExamResponse exam) {
        double sum = 0;
        double weight = 0;
        for (GradeResponse grade : exam.getGrades()) {
            sum += grade.getMark();
            weight++;
        }
        exam.setAverageMark(sum / weight);
    }

    private List<SemesterExamsResponse> loadExamsWithoutAverages(AuthDAO authDAO) {
        switch (authDAO.getRole()) {
            case STUDENT:
                return examRepo.loadExamsByStudent(authDAO.getUserId());
            case TEACHER:
                return examRepo.loadExamsByTeacher(authDAO.getUserId());
            default:
                throw new InvalidException("Role " + authDAO.getRole());
        }
    }

    public ResponseEntity<MessageResponse> createExam(String auth, CreateExamRequest request) {
        authExactRole(auth, UserRole.TEACHER);
        if (request.getWeight() <= 0) {
            throw new InvalidException("Weight should be positive");
        }
        if (!examRepo.hasSchoolClassSubjectAndSemester(request.getSchoolClassId(), request.getSubjectId(), request.getSemesterId())) {
            throw new InvalidException("Cannot create exam as the given school class does not exist in the given semester and subject.");
        }

        examRepo.createExam(request);
        return ResponseEntity.ok(new MessageResponse("Successfully created exam"));
    }

    public ResponseEntity<MessageResponse> changeExam(String auth, ChangeExamRequest request) {
        authExactRole(auth, UserRole.TEACHER);

        if (request.getWeight() <= 0) {
            throw new InvalidException("Weight should be positive");
        }

        examRepo.changeExam(request);
        return ResponseEntity.ok(new MessageResponse("Successfully changed exam"));
    }

    public ResponseEntity<MessageResponse> deleteExam(String auth, UUID examId) {
        authExactRole(auth, UserRole.TEACHER);
        if (examRepo.examUsed(examId)) {
            throw new InvalidException("Exam used by grades.");
        }
        int deleteCount = examRepo.deleteById(EXAM, examId);
        if (deleteCount == 0) {
            throw new NotFoundException("Exam");
        }
        return ResponseEntity.ok(new MessageResponse("Successfully deleted exam"));
    }
}
