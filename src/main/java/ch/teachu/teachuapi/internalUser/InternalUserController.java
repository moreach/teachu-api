package ch.teachu.teachuapi.internalUser;

import ch.teachu.teachuapi.dtos.MessageDTO;
import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.internalUser.dto.ChangeProfileDTO;
import ch.teachu.teachuapi.internalUser.dto.CreateUserDTO;
import ch.teachu.teachuapi.internalUser.dto.PersonalUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User")
@RestController
public class InternalUserController {

    private final InternalUserService internalUserService;

    public InternalUserController(InternalUserService internalUserService) {
        this.internalUserService = internalUserService;
    }

    // TODO remove
    @Operation(summary = "Create user for testing purposes")
    @PostMapping("/user")
    private ResponseEntity<MessageDTO> createUser(@RequestBody CreateUserDTO createUserDTO) {
        return internalUserService.create(createUserDTO);
    }

    @GetMapping("/user")
    private ResponseEntity<PersonalUserDTO> getUser(@RequestHeader("auth") String auth) {
        return internalUserService.getUser(auth);
    }

    @GetMapping("/user/role")
    private ResponseEntity<UserRole> getUserRole(@RequestHeader("auth") String auth) {
        return internalUserService.getUserRole(auth);
    }

    @PutMapping("/user/profile")
    private ResponseEntity<MessageDTO> changeProfile(@RequestHeader("auth") String auth, ChangeProfileDTO changeProfileDTO) {
        return internalUserService.changeProfile(auth, changeProfileDTO);
    }

}
