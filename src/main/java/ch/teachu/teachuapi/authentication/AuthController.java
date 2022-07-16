package ch.teachu.teachuapi.authentication;

import ch.teachu.teachuapi.authentication.dtos.*;
import ch.teachu.teachuapi.parent.AbstractService;
import ch.teachu.teachuapi.parent.dtos.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication")
@RequestMapping("/authentication")
@RestController
public class AuthController extends AbstractService {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "login")
    @PostMapping
    private ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @Operation(summary = "refresh")
    @PutMapping
    private ResponseEntity<RefreshResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
        return authService.refresh(refreshRequest);
    }

    @Operation(summary = "Change password and log out all sessions of this user. The response contains a new token valid token.")
    @PutMapping("/password")
    private ResponseEntity<LoginResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return authService.changePassword(changePasswordRequest);
    }

    @Operation(summary = "logout")
    @DeleteMapping
    private ResponseEntity<MessageResponse> logout(@RequestBody LogoutRequest logoutRequest) {
        return authService.logout(logoutRequest);
    }
}
