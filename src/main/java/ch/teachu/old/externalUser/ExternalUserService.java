//package ch.teachu.old.externalUser;
//
//import ch.teachu.old.enums.UserRole;
//import ch.teachu.old.errorhandling.NotFoundException;
//import ch.teachu.old.parent.AbstractService;
//import ch.teachu.old.externalUser.dto.ExternalUserResponse;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//
//@Service
//public class ExternalUserService extends AbstractService {
//
//    private final ExternalUserRepo externalUserRepo;
//
//    public ExternalUserService(ExternalUserRepo externalUserRepo) {
//        this.externalUserRepo = externalUserRepo;
//    }
//
//    public ResponseEntity<ExternalUserResponse> getUser(String auth, UUID userId) {
//        authMinRole(auth, UserRole.PARENT);
//        return ResponseEntity.ok(externalUserRepo.findById(userId)
//                .orElseThrow(() -> new NotFoundException("User " + userId)));
//    }
//}
