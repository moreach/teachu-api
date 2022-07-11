
package ch.teachu.teachuapi.child;

import ch.teachu.teachuapi.child.dto.OutlineChildrenResponse;
import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.parent.AbstractService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ChildService extends AbstractService {

    private final ChildRepo childRepo;

    public ResponseEntity<OutlineChildrenResponse> getChildren(String auth) {
        UUID parentId = authExactRole(auth, UserRole.PARENT).getUserId();
        return ResponseEntity.ok(new OutlineChildrenResponse(childRepo.findChildren(parentId)));
    }
}
