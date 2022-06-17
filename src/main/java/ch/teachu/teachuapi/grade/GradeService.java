package ch.teachu.teachuapi.grade;

import ch.teachu.teachuapi.child.ChildRepo;
import ch.teachu.teachuapi.configs.GradeProperties;
import ch.teachu.teachuapi.daos.AuthDAO;
import ch.teachu.teachuapi.dtos.MessageResponse;
import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.errorhandling.InvalidException;
import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.errorhandling.UnauthorizedException;
import ch.teachu.teachuapi.generated.tables.Grade;
import ch.teachu.teachuapi.grade.dto.*;
import ch.teachu.teachuapi.parent.AbstractService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GradeService extends AbstractService {

    private final GradeRepo gradeRepo;
    private final ChildRepo childRepo;
    private final GradeProperties gradeProperties;

    public ResponseEntity<GradesResponse> loadWithRestriction(String auth, LoadGradeRequest request) {
        authMinRole(auth, UserRole.TEACHER);
        return ResponseEntity.ok(gradeRepo.loadWithRestriction(request));
    }

    public ResponseEntity<CreateGradeResponse> createGrade(String auth, CreateGradeRequest request, UUID studentId) {
        authMinRole(auth, UserRole.TEACHER);

        if (request.getMark() > gradeProperties.getMaxMark() || request.getMark() < gradeProperties.getMinMark()) {
            throw new InvalidException("Mark not in range.");
        }
        if (gradeRepo.studentHasGrade(studentId, request.getExamId())) {
            throw new InvalidException("Grade already registered");
        }
        if (gradeRepo.studentCanTakeExam(studentId, request.getExamId())) {
            throw new InvalidException("Student is not in the right class for this exam");
        }

        UUID id = gradeRepo.createGrade(request, studentId);
        return ResponseEntity.ok(new CreateGradeResponse(id));
    }

    public ResponseEntity<MessageResponse> changeGrade(String auth, ChangeGradeRequest request, UUID studentId) {
        authMinRole(auth, UserRole.TEACHER);

        if (request.getMark() > gradeProperties.getMaxMark() || request.getMark() < gradeProperties.getMinMark()) {
            throw new InvalidException("Mark not in range.");
        }

        gradeRepo.changeGrade(request, studentId);
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
