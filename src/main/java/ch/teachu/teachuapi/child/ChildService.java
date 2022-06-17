
package ch.teachu.teachuapi.child;

import ch.teachu.teachuapi.child.dto.ChildResponse;
import ch.teachu.teachuapi.child.dto.OutlineChildrenResponse;
import ch.teachu.teachuapi.daos.AuthDAO;
import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.exam.ExamRepo;
import ch.teachu.teachuapi.exam.ExamService;
import ch.teachu.teachuapi.exam.dto.SemesterExamsResponse;
import ch.teachu.teachuapi.generated.tables.User;
import ch.teachu.teachuapi.parent.AbstractService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ChildService extends AbstractService {

    private final ChildRepo childRepo;
    private final ExamService examService;
    private final ExamRepo examRepo;

    public ResponseEntity<OutlineChildrenResponse> getChildren(String auth) {
        UUID parentId = authExactRole(auth, UserRole.PARENT).getUserId();
        return ResponseEntity.ok(new OutlineChildrenResponse(childRepo.findChildren(parentId)));
    }

    public ResponseEntity<ChildResponse> getChild(String auth, UUID studentId) {
        AuthDAO authDAO = authExactRole(auth, UserRole.PARENT);

        if (!childRepo.existsById(User.USER, studentId)) {
            throw new NotFoundException("Student " + studentId);
        }
        if (!childRepo.isParentOfChild(authDAO.getUserId(), studentId)) {
            throw new NotFoundException("Student " + studentId);
        }

        ChildResponse childResponse = childRepo.loadBaseData(authDAO.getUserId(), studentId);
        childResponse.setMarks(loadExams(authDAO, studentId));
        return ResponseEntity.ok(childResponse);
    }

    private List<SemesterExamsResponse> loadExams(AuthDAO authDAO, UUID studentId) {
        List<SemesterExamsResponse> semesters = examRepo.loadExamsByStudent(studentId);
        examService.calculateAverages(semesters);
        return semesters;
    }
}
