
package ch.teachu.teachuapi.child;

import ch.teachu.teachuapi.child.dto.ChildResponse;
import ch.teachu.teachuapi.child.dto.OutlineChildrenResponse;
import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.errorhandling.UnauthorizedException;
import ch.teachu.teachuapi.generated.tables.User;
import ch.teachu.teachuapi.parent.AbstractService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChildService extends AbstractService {

    private final ChildRepo childRepo;

    public ChildService(ChildRepo childRepo) {
        this.childRepo = childRepo;
    }

    public ResponseEntity<OutlineChildrenResponse> getChildren(String auth) {
        UUID parentId = authExactRole(auth, UserRole.PARENT).getUserId();
        return ResponseEntity.ok(new OutlineChildrenResponse(childRepo.findChildren(parentId)));
    }

    public ResponseEntity<ChildResponse> getChild(String auth, UUID studentId) {
        UUID parentId = authExactRole(auth, UserRole.PARENT).getUserId();

        if (!childRepo.existsById(User.USER, studentId)) {
            throw new NotFoundException("Student " + studentId);
        }
        return ResponseEntity.ok(childRepo.findChild(parentId, studentId)
                .orElseThrow(() -> new UnauthorizedException("Invalid child requested")));
    }
}
