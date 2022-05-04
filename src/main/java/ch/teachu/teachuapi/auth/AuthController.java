package ch.teachu.teachuapi.auth;

import ch.teachu.teachuapi.auth.dtos.LoginDTO;
import ch.teachu.teachuapi.auth.dtos.LogoutDTO;
import ch.teachu.teachuapi.auth.dtos.RefreshDTO;
import ch.teachu.teachuapi.auth.dtos.TokenDTO;
import ch.teachu.teachuapi.dtos.MessageDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "auth")
@RestController
public class AuthController {


    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "login")
    @PostMapping("/auth")
    private ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    @Operation(summary = "refresh")
    @PutMapping("/auth")
    private ResponseEntity<TokenDTO> refresh(@RequestBody RefreshDTO refreshDTO) {
        return authService.refresh(refreshDTO);
    }

    @Operation(summary = "logout")
    @DeleteMapping("/auth")
    private ResponseEntity<MessageDTO> logout(@RequestBody LogoutDTO logoutDTO) {
        return authService.logout(logoutDTO);
    }
}
