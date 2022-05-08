package ch.teachu.teachuapi.personalUser;

import ch.teachu.teachuapi.dtos.MessageDTO;
import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.errorhandling.InvalidException;
import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.parent.AbstractService;
import ch.teachu.teachuapi.personalUser.dto.ChangeProfileDTO;
import ch.teachu.teachuapi.personalUser.dto.CreateUserDTO;
import ch.teachu.teachuapi.personalUser.dto.PersonalUserDTO;
import ch.teachu.teachuapi.util.ValidationUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PersonalUserService extends AbstractService {

    private final PersonalUserRepo personalUserRepo;
    private final PasswordEncoder passwordEncoder;

    public PersonalUserService(PersonalUserRepo personalUserRepo, PasswordEncoder passwordEncoder) {
        this.personalUserRepo = personalUserRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<MessageDTO> create(CreateUserDTO createUserDTO) {
        ValidationUtil.checkIfEmailIsValid(createUserDTO.getEmail());
        ValidationUtil.checkIfPasswordIsValid(createUserDTO.getPassword());

        if (personalUserRepo.existsByEmail(createUserDTO.getEmail())) {
            throw new InvalidException(createUserDTO.getEmail());
        }

        personalUserRepo.createUser(createUserDTO.getEmail(), passwordEncoder.encode(createUserDTO.getPassword()), UserRole.ADMIN);
        return ResponseEntity.ok().body(new MessageDTO("Successfully created user"));
    }

    public ResponseEntity<PersonalUserDTO> getUser(String auth) {
        UUID userId = authenticate(auth, UserRole.PARENT).getUserId();
        return ResponseEntity.ok(personalUserRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User " + userId)));
    }

    public ResponseEntity<MessageDTO> changeProfile(String auth, ChangeProfileDTO changeProfileDTO) {
        UUID userId = authenticate(auth, UserRole.PARENT).getUserId();
        personalUserRepo.changeProfile(userId, changeProfileDTO);
        return ResponseEntity.ok(new MessageDTO("Successfully changed profile"));
    }

    public ResponseEntity<UserRole> getUserRole(String auth) {
        return ResponseEntity.ok(authenticate(auth, UserRole.PARENT).getRole());
    }
}
