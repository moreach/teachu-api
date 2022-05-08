package ch.teachu.teachuapi.internalUser;

import ch.teachu.teachuapi.dtos.MessageDTO;
import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.errorhandling.InvalidException;
import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.internalUser.dto.ChangeProfileDTO;
import ch.teachu.teachuapi.internalUser.dto.CreateUserDTO;
import ch.teachu.teachuapi.internalUser.dto.PersonalUserDTO;
import ch.teachu.teachuapi.parent.AbstractService;
import ch.teachu.teachuapi.util.ValidationUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InternalUserService extends AbstractService {

    private final InternalUserRepo internalUserRepo;
    private final PasswordEncoder passwordEncoder;

    public InternalUserService(InternalUserRepo internalUserRepo, PasswordEncoder passwordEncoder) {
        this.internalUserRepo = internalUserRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<MessageDTO> create(CreateUserDTO createUserDTO) {
        ValidationUtil.checkIfEmailIsValid(createUserDTO.getEmail());
        ValidationUtil.checkIfPasswordIsValid(createUserDTO.getPassword());

        if (internalUserRepo.existsByEmail(createUserDTO.getEmail())) {
            throw new InvalidException(createUserDTO.getEmail());
        }

        internalUserRepo.createUser(createUserDTO.getEmail(), passwordEncoder.encode(createUserDTO.getPassword()), UserRole.ADMIN);
        return ResponseEntity.ok().body(new MessageDTO("Successfully created user"));
    }

    public ResponseEntity<PersonalUserDTO> getUser(String auth) {
        UUID userId = authenticate(auth, UserRole.PARENT).getUserId();
        return ResponseEntity.ok(internalUserRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User " + userId)));
    }

    public ResponseEntity<MessageDTO> changeProfile(String auth, ChangeProfileDTO changeProfileDTO) {
        UUID userId = authenticate(auth, UserRole.PARENT).getUserId();
        internalUserRepo.changeProfile(userId, changeProfileDTO);
        return ResponseEntity.ok(new MessageDTO("Successfully changed profile"));
    }
}
