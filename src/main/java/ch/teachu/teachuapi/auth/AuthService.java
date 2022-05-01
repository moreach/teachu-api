package ch.teachu.teachuapi.auth;

import ch.teachu.teachuapi.auth.dtos.LoginDTO;
import ch.teachu.teachuapi.auth.dtos.TokenDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    public ResponseEntity<TokenDTO> login(LoginDTO loginDTO) {



        return ResponseEntity.ok(new TokenDTO());
    }
}
