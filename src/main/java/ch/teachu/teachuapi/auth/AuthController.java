package ch.teachu.teachuapi.auth;

import ch.teachu.teachuapi.auth.dtos.LoginRequest;
import ch.teachu.teachuapi.auth.dtos.LogoutRequest;
import ch.teachu.teachuapi.auth.dtos.RefreshRequest;
import ch.teachu.teachuapi.auth.dtos.TokenResponse;
import ch.teachu.teachuapi.dtos.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth")
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "login")
    @PostMapping
    private ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @Operation(summary = "refresh")
    @PutMapping
    private ResponseEntity<TokenResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
        return authService.refresh(refreshRequest);
    }

    @Operation(summary = "logout")
    @DeleteMapping
    private ResponseEntity<MessageResponse> logout(@RequestBody LogoutRequest logoutRequest) {
        return authService.logout(logoutRequest);
    }
}
