package ch.teachu.teachuapi.auth;

import ch.teachu.teachuapi.auth.dtos.LoginDTO;
import ch.teachu.teachuapi.auth.dtos.TokenDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "auth")
@RestController
public class AuthController {


    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "login")
    @PostMapping("/auth")
    private ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }
}
