package ch.teachu.teachuapi.exam;

import ch.teachu.teachuapi.dtos.MessageResponse;
import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.errorhandling.InvalidException;
import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.exam.dto.ChangeExamRequest;
import ch.teachu.teachuapi.exam.dto.CreateExamRequest;
import ch.teachu.teachuapi.exam.dto.ExamHasGradesResponse;
import ch.teachu.teachuapi.exam.dto.ExamsResponse;
import ch.teachu.teachuapi.generated.tables.Exam;
import ch.teachu.teachuapi.parent.AbstractService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExamService extends AbstractService {

    private final ExamRepo examRepo;

    public ExamService(ExamRepo examRepo) {
        this.examRepo = examRepo;
    }

    public ResponseEntity<ExamsResponse> loadExams(String auth) {
        UUID teacherId = authExactRole(auth, UserRole.TEACHER).getUserId();
        return ResponseEntity.ok(new ExamsResponse(examRepo.loadExams(teacherId)));
    }

    public ResponseEntity<MessageResponse> createExam(String auth, CreateExamRequest request) {
        authExactRole(auth, UserRole.TEACHER);
        examRepo.createExam(request);
        return ResponseEntity.ok(new MessageResponse("Successfully created exam"));
    }

    public ResponseEntity<MessageResponse> changeExam(String auth, ChangeExamRequest request) {
        authExactRole(auth, UserRole.TEACHER);
        examRepo.changeExam(request);
        return ResponseEntity.ok(new MessageResponse("Successfully changed exam"));
    }

    public ResponseEntity<MessageResponse> deleteExam(String auth, UUID examId) {
        authExactRole(auth, UserRole.TEACHER);
        if (examRepo.examUsed(examId)) {
            throw new InvalidException("Exam used by grades.");
        }
        int deleteCount = examRepo.deleteById(Exam.EXAM, examId);
        if (deleteCount == 0) {
            throw new NotFoundException("Exam");
        }
        return ResponseEntity.ok(new MessageResponse("Successfully deleted exam"));
    }

    public ResponseEntity<ExamHasGradesResponse> hasGrades(String auth, UUID examId) {
        authMinRole(auth, UserRole.TEACHER);
        return ResponseEntity.ok(new ExamHasGradesResponse(examRepo.examUsed(examId)));
    }
}
