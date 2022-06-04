package ch.teachu.teachuapi.internalUser;

import ch.teachu.teachuapi.auth.AbstractSecurityService;
import ch.teachu.teachuapi.auth.AuthRepo;
import ch.teachu.teachuapi.auth.AuthService;
import ch.teachu.teachuapi.auth.dtos.LoginRequest;
import ch.teachu.teachuapi.auth.dtos.TokenResponse;
import ch.teachu.teachuapi.dtos.MessageResponse;
import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.errorhandling.InvalidException;
import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.internalUser.dto.*;
import ch.teachu.teachuapi.util.ValidationUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InternalUserService extends AbstractSecurityService {

    private final InternalUserRepo internalUserRepo;
    private final AuthRepo authRepo;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    public InternalUserService(PasswordEncoder passwordEncoder, AuthRepo authRepo, InternalUserRepo internalUserRepo, AuthService authService) {
        super(passwordEncoder, authRepo);
        this.internalUserRepo = internalUserRepo;
        this.authRepo = authRepo;
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<MessageResponse> create(CreateUserRequest createUserRequest) {
        ValidationUtil.checkIfEmailIsValid(createUserRequest.getEmail());
        ValidationUtil.checkIfPasswordIsValid(createUserRequest.getPassword());

        if (internalUserRepo.existsByEmail(createUserRequest.getEmail())) {
            throw new InvalidException(createUserRequest.getEmail());
        }

        internalUserRepo.createUser(createUserRequest.getEmail(), passwordEncoder.encode(createUserRequest.getPassword()), UserRole.ADMIN);
        return ResponseEntity.ok().body(new MessageResponse("Successfully created user"));
    }

    public ResponseEntity<InternalUserResponse> getUser(String auth) {
        UUID userId = authMinRole(auth, UserRole.PARENT).getUserId();
        return ResponseEntity.ok(internalUserRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User " + userId)));
    }

    public ResponseEntity<MessageResponse> changeProfile(String auth, ChangeProfileRequest changeProfileRequest) {
        UUID userId = authMinRole(auth, UserRole.PARENT).getUserId();
        internalUserRepo.changeProfile(userId, changeProfileRequest);
        return ResponseEntity.ok(new MessageResponse("Successfully changed profile"));
    }

    public ResponseEntity<TokenResponse> changePassword(ChangePasswordRequest changePasswordRequest) {
        UUID userId = login(changePasswordRequest.getEmail(), changePasswordRequest.getOldPassword()).getId();
        ValidationUtil.checkIfPasswordIsValid(changePasswordRequest.getNewPassword());
        internalUserRepo.changePassword(userId, passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        authRepo.deleteTokensByUserId(userId);
        return authService.login(new LoginRequest(changePasswordRequest.getEmail(), changePasswordRequest.getNewPassword()));
    }

    public ResponseEntity<MessageResponse> changeDarkTheme(String auth, ChangeDarkThemeRequest request) {
        UUID userId = authMinRole(auth, UserRole.PARENT).getUserId();
        internalUserRepo.changeDarkTheme(userId, request);
        return ResponseEntity.ok(new MessageResponse("Successfully changed dark theme"));
    }
}
