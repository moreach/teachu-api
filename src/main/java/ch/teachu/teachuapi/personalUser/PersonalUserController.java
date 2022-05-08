package ch.teachu.teachuapi.personalUser;

import ch.teachu.teachuapi.dtos.MessageDTO;
import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.personalUser.dto.ChangeProfileDTO;
import ch.teachu.teachuapi.personalUser.dto.CreateUserDTO;
import ch.teachu.teachuapi.personalUser.dto.PersonalUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User")
@RestController
public class PersonalUserController {

    private final PersonalUserService personalUserService;

    public PersonalUserController(PersonalUserService personalUserService) {
        this.personalUserService = personalUserService;
    }

    // TODO remove
    @Operation(summary = "Create user for testing purposes")
    @PostMapping("/user")
    private ResponseEntity<MessageDTO> createUser(@RequestBody CreateUserDTO createUserDTO) {
        return personalUserService.create(createUserDTO);
    }

    @GetMapping("/user")
    private ResponseEntity<PersonalUserDTO> getUser(@RequestHeader("auth") String auth) {
        return personalUserService.getUser(auth);
    }

    @GetMapping("/user/role")
    private ResponseEntity<UserRole> getUserRole(@RequestHeader("auth") String auth) {
        return personalUserService.getUserRole(auth);
    }

    @PutMapping("/user/profile")
    private ResponseEntity<MessageDTO> changeProfile(@RequestHeader("auth") String auth, ChangeProfileDTO changeProfileDTO) {
        return personalUserService.changeProfile(auth, changeProfileDTO);
    }

}
