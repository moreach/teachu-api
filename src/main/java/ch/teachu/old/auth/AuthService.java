//package ch.teachu.old.auth;
//
//import ch.teachu.old.auth.daos.AuthUserDAO;
//import ch.teachu.old.auth.dtos.LoginRequest;
//import ch.teachu.old.auth.dtos.LogoutRequest;
//import ch.teachu.old.auth.dtos.RefreshRequest;
//import ch.teachu.old.auth.dtos.TokenResponse;
//import ch.teachu.old.configs.SecurityProperties;
//import ch.teachu.old.dtos.MessageResponse;
//import ch.teachu.old.errorhandling.NotFoundException;
//import ch.teachu.old.internalUser.InternalUserRepo;
//import ch.teachu.old.internalUser.dto.ChangePasswordRequest;
//import ch.teachu.old.util.Assert;
//import ch.teachu.old.util.DateUtil;
//import ch.teachu.old.util.ValidationUtil;
//import ch.teachu.teachuapi.generated.tables.records.TokenRecord;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.UUID;
//
//@Service
//public class AuthService extends AbstractSecurityService {
//
//    private final SecurityProperties securityProperties;
//    private final InternalUserRepo internalUserRepo;
//
//    public AuthService(PasswordEncoder passwordEncoder, AuthRepo authRepo, SecurityProperties securityProperties, InternalUserRepo internalUserRepo) {
//        super(passwordEncoder, authRepo);
//        this.securityProperties = securityProperties;
//        this.internalUserRepo = internalUserRepo;
//    }
//
//    public ResponseEntity<TokenResponse> login(LoginRequest loginRequest) {
//        AuthUserDAO user = login(loginRequest.getEmail(), loginRequest.getPassword());
//
//        TokenRecord tokenRecord = authRepo.storeToken(RandomStringUtils.randomAlphanumeric(10), RandomStringUtils.randomAlphanumeric(10), calculateAccessExpires(), calculateRefreshExpires(), user.getId());
//
//        TokenResponse tokenResponse = new TokenResponse(tokenRecord.getAccess(), tokenRecord.getRefresh(), DateUtil.toDate(tokenRecord.getRefreshExpires()));
//
//        return ResponseEntity.ok(tokenResponse);
//    }
//
//    public ResponseEntity<TokenResponse> refresh(RefreshRequest refreshRequest) {
//        Assert.ensureTrue(authRepo.isUserActive(refreshRequest.getRefresh()), "User deactivated");
//
//        TokenRecord tokenRecord = authRepo.findTokenByRefresh(refreshRequest.getRefresh())
//                .orElseThrow(() -> new NotFoundException("Could not find refresh " + refreshRequest.getRefresh()));
//
//        tokenRecord.setAccess(RandomStringUtils.randomAlphanumeric(10));
//
//        tokenRecord.setAccessExpires(calculateAccessExpires());
//
//        tokenRecord.setRefreshExpires(calculateRefreshExpires());
//
//        tokenRecord.store();
//
//        TokenResponse tokenResponse = new TokenResponse(tokenRecord.getAccess(), tokenRecord.getRefresh(), DateUtil.toDate(tokenRecord.getRefreshExpires()));
//
//        return ResponseEntity.ok(tokenResponse);
//    }
//
//    public ResponseEntity<MessageResponse> logout(LogoutRequest logoutRequest) {
//        int count = authRepo.deleteTokenByRefresh(logoutRequest.getRefresh());
//        if (count == 0) {
//            throw new NotFoundException("Could not delete refresh token");
//        }
//        return ResponseEntity.ok(new MessageResponse("Successfully deleted token"));
//    }
//
//    public ResponseEntity<TokenResponse> changePassword(ChangePasswordRequest changePasswordRequest) {
//        UUID userId = login(changePasswordRequest.getEmail(), changePasswordRequest.getOldPassword()).getId();
//
//        ValidationUtil.checkIfPasswordIsValid(changePasswordRequest.getNewPassword());
//
//        internalUserRepo.changePassword(userId, passwordEncoder.encode(changePasswordRequest.getNewPassword()));
//
//        authRepo.deleteTokensByUserId(userId);
//
//        return login(new LoginRequest(changePasswordRequest.getEmail(), changePasswordRequest.getNewPassword()));
//    }
//
//    private LocalDateTime calculateAccessExpires() {
//        return LocalDateTime.now().plus(securityProperties.getAccessExpiresInHours(), ChronoUnit.HOURS);
//    }
//
//    private LocalDateTime calculateRefreshExpires() {
//        return LocalDateTime.now().plus(securityProperties.getRefreshExpiresInHours(), ChronoUnit.HOURS);
//    }
//}
