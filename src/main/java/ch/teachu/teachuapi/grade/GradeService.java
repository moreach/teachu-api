package ch.teachu.teachuapi.grade;

import ch.teachu.teachuapi.daos.AuthDAO;
import ch.teachu.teachuapi.dtos.MessageResponse;
import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.errorhandling.InvalidException;
import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.generated.tables.Grade;
import ch.teachu.teachuapi.grade.dto.*;
import ch.teachu.teachuapi.parent.AbstractService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GradeService extends AbstractService {

    private final GradeRepo gradeRepo;

    public GradeService(GradeRepo gradeRepo) {
        this.gradeRepo = gradeRepo;
    }

    public ResponseEntity<SemestersGradesResponse> loadGrades(String auth) {
        AuthDAO authDAO = authMinRole(auth, UserRole.STUDENT);
        List<SemesterGradesDTO> result = null;
        switch (authDAO.getRole()) {
            case STUDENT:
                result = gradeRepo.loadStudentGrades(authDAO.getUserId());
                break;
            case TEACHER:
                result = gradeRepo.loadTeacherGrades(authDAO.getUserId());
                break;
            default:
                throwUnauthorizedExact(authDAO.getRole(), UserRole.STUDENT, UserRole.TEACHER);
        }
        return ResponseEntity.ok(new SemestersGradesResponse(result));
    }

    public ResponseEntity<GradesResponse> loadGrades(String auth, UUID examId) {
        authMinRole(auth, UserRole.TEACHER);
        return ResponseEntity.ok(new GradesResponse(gradeRepo.loadByExam(examId)));
    }

    public ResponseEntity<MessageResponse> createGrade(String auth, CreateGradeRequest request) {
        authMinRole(auth, UserRole.TEACHER);
        gradeRepo.createGrade(request);
        return ResponseEntity.ok(new MessageResponse("Successfully created grade"));
    }

    public ResponseEntity<MessageResponse> changeGrade(String auth, ChangeGradeRequest request) {
        authMinRole(auth, UserRole.TEACHER);
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
