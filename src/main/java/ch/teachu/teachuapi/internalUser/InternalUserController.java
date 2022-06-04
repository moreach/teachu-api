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

    // TODO remove
    @Operation(summary = "Create user for testing purposes")
    @PostMapping
    private ResponseEntity<MessageResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        return internalUserService.create(createUserRequest);
    }

    @Operation(summary = "User data and settings of logged in user")
    @GetMapping
    private ResponseEntity<InternalUserResponse> getUser(@RequestHeader("auth") String auth) {
        return internalUserService.getUser(auth);
    }

    @Operation(summary = "Change user data and settings of logged in user")
    @PutMapping("/profile")
    private ResponseEntity<MessageResponse> changeProfile(@RequestHeader("auth") String auth, @RequestBody ChangeProfileRequest changeProfileRequest) {
        return internalUserService.changeProfile(auth, changeProfileRequest);
    }

    @Operation(summary = "Change password and log out all sessions of this user. The response contains a new token valid token.")
    @PutMapping("/password")
    private ResponseEntity<TokenResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return internalUserService.changePassword(changePasswordRequest);
    }

    @Operation(summary = "Change the dark theme")
    @PutMapping("/darkTheme")
    private ResponseEntity<MessageResponse> changeDarkTheme(@RequestHeader("auth") String auth, ChangeDarkThemeRequest request) {
        return internalUserService.changeDarkTheme(auth, request);
    }
}
