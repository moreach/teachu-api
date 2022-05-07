package ch.teachu.teachuapi.user;

import ch.teachu.teachuapi.dtos.MessageDTO;
import ch.teachu.teachuapi.user.dto.CreateUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create account")
    @PostMapping("/account")
    private ResponseEntity<MessageDTO> createAccount(@RequestBody CreateUserDTO createUserDTO) {
        return userService.create(createUserDTO);
    }
}
