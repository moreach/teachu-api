package ch.teachu.teachuapi.user;

import ch.teachu.teachuapi.dtos.MessageDTO;
import ch.teachu.teachuapi.enums.Role;
import ch.teachu.teachuapi.errorhandling.InvalidException;
import ch.teachu.teachuapi.parent.AbstractService;
import ch.teachu.teachuapi.user.dto.CreateUserDTO;
import ch.teachu.teachuapi.util.ValidationUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<MessageDTO> create(CreateUserDTO createUserDTO) {
        ValidationUtil.checkIfEmailIsValid(createUserDTO.getEmail());
        ValidationUtil.checkIfPasswordIsValid(createUserDTO.getPassword());

        if (userRepo.existsByEmail(createUserDTO.getEmail())) {
            throw new InvalidException(createUserDTO.getEmail());
        }

        userRepo.createUser(createUserDTO.getEmail(), passwordEncoder.encode(createUserDTO.getPassword()), Role.ADMIN);
        return ResponseEntity.ok().body(new MessageDTO("Successfully created account"));
    }
}
