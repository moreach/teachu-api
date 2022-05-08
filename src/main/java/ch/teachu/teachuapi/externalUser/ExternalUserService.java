package ch.teachu.teachuapi.externalUser;

import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.externalUser.dto.ExternalUserDTO;
import ch.teachu.teachuapi.parent.AbstractService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExternalUserService extends AbstractService {

    private final ExternalUserRepo externalUserRepo;

    public ExternalUserService(ExternalUserRepo externalUserRepo) {
        this.externalUserRepo = externalUserRepo;
    }

    public ResponseEntity<ExternalUserDTO> getUser(String auth, UUID userId) {
        authenticate(auth, UserRole.PARENT);
        return ResponseEntity.ok(externalUserRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User " + userId)));
    }
}
