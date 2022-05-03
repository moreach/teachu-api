package ch.teachu.teachuapi.auth;

import ch.teachu.teachuapi.auth.dtos.LoginDTO;
import ch.teachu.teachuapi.auth.dtos.LogoutDTO;
import ch.teachu.teachuapi.auth.dtos.RefreshDTO;
import ch.teachu.teachuapi.auth.dtos.TokenDTO;
import ch.teachu.teachuapi.dtos.MessageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    public ResponseEntity<TokenDTO> login(LoginDTO loginDTO) {



        return ResponseEntity.ok(new TokenDTO());
    }

    public ResponseEntity<TokenDTO> refresh(RefreshDTO refreshDTO) {
        return null;
    }

    public ResponseEntity<MessageDTO> logout(LogoutDTO logoutDTO) {
        return null;
    }
}
