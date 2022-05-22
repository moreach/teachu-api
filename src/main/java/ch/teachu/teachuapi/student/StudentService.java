package ch.teachu.teachuapi.student;

import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.parent.AbstractService;
import ch.teachu.teachuapi.student.dto.StudentSemestersReponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StudentService extends AbstractService {

    private final StudentRepo studentRepo;

    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public ResponseEntity<StudentSemestersReponse> loadMarks(String auth) {
        UUID studentId = authExactRole(auth, UserRole.STUDENT).getUserId();
        return ResponseEntity.ok(new StudentSemestersReponse(studentRepo.loadMarks(studentId)));
    }
}
