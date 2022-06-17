package ch.teachu.teachuapi.auth;

import ch.teachu.teachuapi.auth.daos.AuthUserDAO;
import ch.teachu.teachuapi.auth.dtos.LoginRequest;
import ch.teachu.teachuapi.auth.dtos.LogoutRequest;
import ch.teachu.teachuapi.auth.dtos.RefreshRequest;
import ch.teachu.teachuapi.auth.dtos.TokenResponse;
import ch.teachu.teachuapi.configs.SecurityProperties;
import ch.teachu.teachuapi.dtos.MessageResponse;
import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.generated.tables.records.TokenRecord;
import ch.teachu.teachuapi.internalUser.InternalUserRepo;
import ch.teachu.teachuapi.internalUser.dto.ChangePasswordRequest;
import ch.teachu.teachuapi.util.DateUtil;
import ch.teachu.teachuapi.util.ValidationUtil;
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
    private final InternalUserRepo internalUserRepo;

    public AuthService(PasswordEncoder passwordEncoder, AuthRepo authRepo, SecurityProperties securityProperties, InternalUserRepo internalUserRepo) {
        super(passwordEncoder, authRepo);
        this.securityProperties = securityProperties;
        this.internalUserRepo = internalUserRepo;
    }

    public ResponseEntity<TokenResponse> login(LoginRequest loginRequest) {
        AuthUserDAO user = login(loginRequest.getEmail(), loginRequest.getPassword());
        TokenRecord tokenRecord = authRepo.storeToken(RandomStringUtils.randomAlphanumeric(10), RandomStringUtils.randomAlphanumeric(10), calculateAccessExpires(), calculateRefreshExpires(), user.getId());
        TokenResponse tokenResponse = new TokenResponse(tokenRecord.getAccess(), tokenRecord.getRefresh(), DateUtil.toDate(tokenRecord.getRefreshExpires()));
        return ResponseEntity.ok(tokenResponse);
    }

    public ResponseEntity<TokenResponse> refresh(RefreshRequest refreshRequest) {
        TokenRecord tokenRecord = authRepo.findTokenByRefresh(refreshRequest.getRefresh())
                .orElseThrow(() -> new NotFoundException("Could not find refresh " + refreshRequest.getRefresh()));

        tokenRecord.setAccess(RandomStringUtils.randomAlphanumeric(10));
        tokenRecord.setAccessExpires(calculateAccessExpires());
        tokenRecord.setRefreshExpires(calculateRefreshExpires());
        tokenRecord.store();

        TokenResponse tokenResponse = new TokenResponse(tokenRecord.getAccess(), tokenRecord.getRefresh(), DateUtil.toDate(tokenRecord.getRefreshExpires()));
        return ResponseEntity.ok(tokenResponse);
    }

    public ResponseEntity<MessageResponse> logout(LogoutRequest logoutRequest) {
        int count = authRepo.deleteTokenByRefresh(logoutRequest.getRefresh());
        if (count == 0) {
            throw new NotFoundException("Could not delete refresh token");
        }
        return ResponseEntity.ok(new MessageResponse("Successfully deleted token"));
    }

    public ResponseEntity<TokenResponse> changePassword(ChangePasswordRequest changePasswordRequest) {
        UUID userId = login(changePasswordRequest.getEmail(), changePasswordRequest.getOldPassword()).getId();
        ValidationUtil.checkIfPasswordIsValid(changePasswordRequest.getNewPassword());
        internalUserRepo.changePassword(userId, passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        authRepo.deleteTokensByUserId(userId);
        return login(new LoginRequest(changePasswordRequest.getEmail(), changePasswordRequest.getNewPassword()));
    }

    private LocalDateTime calculateAccessExpires() {
        return LocalDateTime.now().plus(securityProperties.getAccessExpiresInHours(), ChronoUnit.HOURS);
    }

    private LocalDateTime calculateRefreshExpires() {
        return LocalDateTime.now().plus(securityProperties.getRefreshExpiresInHours(), ChronoUnit.HOURS);
    }
}
