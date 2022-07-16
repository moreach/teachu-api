//
//package ch.teachu.old.child;
//
//import ch.teachu.old.child.dto.OutlineChildrenResponse;
//import ch.teachu.old.enums.UserRole;
//import ch.teachu.old.parent.AbstractService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//
//@Service
//@AllArgsConstructor
//public class ChildService extends AbstractService {
//
//    private final ChildRepo childRepo;
//
//    public ResponseEntity<OutlineChildrenResponse> getChildren(String auth) {
//        UUID parentId = authExactRole(auth, UserRole.PARENT).getUserId();
//        return ResponseEntity.ok(new OutlineChildrenResponse(childRepo.findChildren(parentId)));
//    }
//}
