package ch.teachu.teachuapi.internalUser;

import ch.teachu.teachuapi.auth.dtos.TokenResponse;
import ch.teachu.teachuapi.dtos.MessageResponse;
import ch.teachu.teachuapi.internalUser.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User")
@RequestMapping("/user")
@RestController
public class InternalUserController {

    private final InternalUserService internalUserService;

    public InternalUserController(InternalUserService internalUserService) {
        this.internalUserService = internalUserService;
    }

    @Operation(summary = "User data and settings of logged in user")
    @GetMapping
    private ResponseEntity<InternalUserResponse> getUser(@RequestHeader("auth") String auth) {
        return internalUserService.getUser(auth);
    }

    // TODO remove
    @Operation(summary = "Create user for testing purposes")
    @PostMapping
    private ResponseEntity<MessageResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        return internalUserService.create(createUserRequest);
    }

    @Operation(summary = "Change user data and settings of logged in user")
    @PutMapping
    private ResponseEntity<MessageResponse> changeProfile(@RequestHeader("auth") String auth, @RequestBody ChangeProfileRequest changeProfileRequest) {
        return internalUserService.changeProfile(auth, changeProfileRequest);
    }
}
