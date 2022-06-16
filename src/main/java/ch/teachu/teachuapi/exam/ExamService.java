package ch.teachu.teachuapi.exam;

import ch.teachu.teachuapi.dtos.MessageResponse;
import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.errorhandling.InvalidException;
import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.exam.dto.ChangeExamRequest;
import ch.teachu.teachuapi.exam.dto.CreateExamRequest;
import ch.teachu.teachuapi.exam.dto.SemestersExamsResponse;
import ch.teachu.teachuapi.parent.AbstractService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static ch.teachu.teachuapi.generated.tables.Exam.EXAM;

@Service
public class ExamService extends AbstractService {

    private final ExamRepo examRepo;

    public ExamService(ExamRepo examRepo) {
        this.examRepo = examRepo;
    }

    public ResponseEntity<SemestersExamsResponse> loadExams(String auth) {
        UUID teacherId = authExactRole(auth, UserRole.TEACHER).getUserId();
        return ResponseEntity.ok(new SemestersExamsResponse(examRepo.loadExams(teacherId)));
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
