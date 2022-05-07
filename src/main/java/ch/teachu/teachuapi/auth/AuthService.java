package ch.teachu.teachuapi.auth;

import ch.teachu.teachuapi.auth.daos.AuthUserDao;
import ch.teachu.teachuapi.auth.dtos.LoginDTO;
import ch.teachu.teachuapi.auth.dtos.LogoutDTO;
import ch.teachu.teachuapi.auth.dtos.RefreshDTO;
import ch.teachu.teachuapi.auth.dtos.TokenDTO;
import ch.teachu.teachuapi.configs.SecurityProperties;
import ch.teachu.teachuapi.dtos.MessageDTO;
import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.util.DateUtil;
import ch.teachu.techuapi.generated.tables.records.TokenRecord;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class AuthService extends AbstractSecurityService {

    private final SecurityProperties securityProperties;

    public AuthService(PasswordEncoder passwordEncoder, AuthRepo authRepo, SecurityProperties securityProperties) {
        super(passwordEncoder, authRepo);
        this.securityProperties = securityProperties;
    }

    public ResponseEntity<TokenDTO> login(LoginDTO loginDTO) {
        AuthUserDao user = login(loginDTO.getEmail(), loginDTO.getPassword());
        TokenRecord tokenRecord = authRepo.storeToken(RandomStringUtils.randomAlphanumeric(10), RandomStringUtils.randomAlphanumeric(10), calculateAccessExpires(), calculateRefreshExpires(), user.getId());
        TokenDTO tokenDTO = new TokenDTO(tokenRecord.getAccess(), tokenRecord.getRefresh(), DateUtil.toDate(tokenRecord.getRefreshExpires()));
        return ResponseEntity.ok(tokenDTO);
    }

    public ResponseEntity<TokenDTO> refresh(RefreshDTO refreshDTO) {
        TokenRecord tokenRecord = authRepo.findTokenByRefresh(refreshDTO.getRefresh())
                .orElseThrow(() -> new NotFoundException("Could not find refresh " + refreshDTO.getRefresh()));

        tokenRecord.setAccess(RandomStringUtils.randomAlphanumeric(10));
        tokenRecord.setAccessExpires(calculateAccessExpires());
        tokenRecord.setRefreshExpires(calculateRefreshExpires());
        tokenRecord.store();

        TokenDTO tokenDTO = new TokenDTO(tokenRecord.getAccess(), tokenRecord.getRefresh(), DateUtil.toDate(tokenRecord.getRefreshExpires()));
        return ResponseEntity.ok(tokenDTO);
    }

    public ResponseEntity<MessageDTO> logout(LogoutDTO logoutDTO) {
        int count = authRepo.deleteTokenByRefresh(logoutDTO.getRefresh());
        if (count == 0) {
            throw new NotFoundException("Could not delete refresh token");
        }
        return ResponseEntity.ok(new MessageDTO("Successfully deleted token"));
    }

    protected LocalDateTime calculateAccessExpires() {
        return LocalDateTime.now().plus(securityProperties.getAccessExpiresInHours(), ChronoUnit.HOURS);
    }

    protected LocalDateTime calculateRefreshExpires() {
        return LocalDateTime.now().plus(securityProperties.getRefreshExpiresInHours(), ChronoUnit.HOURS);
    }
}
