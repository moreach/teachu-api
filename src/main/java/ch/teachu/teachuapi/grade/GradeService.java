package ch.teachu.teachuapi.grade;

import ch.teachu.teachuapi.configs.GradeProperties;
import ch.teachu.teachuapi.dtos.MessageResponse;
import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.errorhandling.InvalidException;
import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.generated.tables.Grade;
import ch.teachu.teachuapi.grade.dto.*;
import ch.teachu.teachuapi.parent.AbstractService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GradeService extends AbstractService {

    private final GradeRepo gradeRepo;
    private final GradeProperties gradeProperties;

    public GradeService(GradeRepo gradeRepo, GradeProperties gradeProperties) {
        this.gradeRepo = gradeRepo;
        this.gradeProperties = gradeProperties;
    }

    public ResponseEntity<SemestersGradesResponse> loadGrades(String auth) {
        UUID userId = authExactRole(auth, UserRole.STUDENT).getUserId();
        return ResponseEntity.ok(new SemestersGradesResponse(gradeRepo.loadGrades(userId)));
    }

    public ResponseEntity<GradesResponse> loadWithRestriction(String auth, LoadGradeRequest request) {
        authMinRole(auth, UserRole.TEACHER);
        return ResponseEntity.ok(gradeRepo.loadWithRestriction(request));
    }

    public ResponseEntity<MessageResponse> createGrade(String auth, CreateGradeRequest request) {
        authMinRole(auth, UserRole.TEACHER);

        if (request.getMark() > gradeProperties.getMaxMark() || request.getMark() < gradeProperties.getMinMark()) {
            throw new InvalidException("Mark not in range.");
        }
        if (gradeRepo.studentHasGrade(request.getStudentId(), request.getExamId())) {
            throw new InvalidException("Grade already registered");
        }
        if (gradeRepo.studentCanTakeExam(request.getStudentId(), request.getExamId())) {
            throw new InvalidException("Student is not in the right class for this exam");
        }

        gradeRepo.createGrade(request);
        return ResponseEntity.ok(new MessageResponse("Successfully created grade"));
    }

    public ResponseEntity<MessageResponse> changeGrade(String auth, ChangeGradeRequest request) {
        authMinRole(auth, UserRole.TEACHER);

        if (request.getMark() > gradeProperties.getMaxMark() || request.getMark() < gradeProperties.getMinMark()) {
            throw new InvalidException("Mark not in range.");
        }

        gradeRepo.changeGrade(request);
        return ResponseEntity.ok(new MessageResponse("Successfully changed grade"));
    }

    public ResponseEntity<MessageResponse> deleteGrade(String auth, UUID gradeId) {
        authMinRole(auth, UserRole.TEACHER);
        int deleteCount = gradeRepo.deleteById(Grade.GRADE, gradeId);
        if (deleteCount == 0) {
            throw new NotFoundException("Grade");
        }
        return ResponseEntity.ok(new MessageResponse("Successfully deleted grade"));
    }
}
