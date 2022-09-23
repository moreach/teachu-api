package ch.teachu.teachuapi.authentication;

import ch.teachu.teachuapi.authentication.dtos.*;
import ch.teachu.teachuapi.shared.AbstractService;
import ch.teachu.teachuapi.shared.dtos.MessageResponse;
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
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @Operation(summary = "refresh")
    @PutMapping
    private ResponseEntity<RefreshResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
        return ResponseEntity.ok(authService.refresh(refreshRequest));
    }

    @Operation(summary = "Change password and log out all sessions of this user. The response contains a new token valid token.")
    @PutMapping("/password")
    private ResponseEntity<LoginResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return ResponseEntity.ok(authService.changePassword(changePasswordRequest));
    }

    @Operation(summary = "logout")
    @DeleteMapping
    private ResponseEntity<MessageResponse> logout(@RequestBody LogoutRequest logoutRequest) {
        return ResponseEntity.ok(authService.logout(logoutRequest));
    }

    // @Operation(summary = "sync data")
    // @GetMapping("/sync")
    // private ResponseEntity<List<UserSyncDataResponse>> syncData() {
    //     return ResponseEntity.ok(authService.syncData());
    // }

    // @Operation(summary = "sync data")
    // @PostMapping("/sync")
    // private ResponseEntity<List<UserSyncDataResponse>> syncData(@RequestBody List<UserSyncDataResponse> syncData) {
    //     authService.syncData(syncData);
    //     return ResponseEntity.ok();
    // }
}
