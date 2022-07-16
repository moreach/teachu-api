//package ch.teachu.old.internalUser;
//
//import ch.teachu.old.auth.AbstractSecurityService;
//import ch.teachu.old.auth.AuthRepo;
//import ch.teachu.old.dtos.MessageResponse;
//import ch.teachu.old.enums.UserRole;
//import ch.teachu.old.errorhandling.InvalidException;
//import ch.teachu.old.errorhandling.NotFoundException;
//import ch.teachu.old.internalUser.dto.ChangeProfileRequest;
//import ch.teachu.old.internalUser.dto.CreateUserRequest;
//import ch.teachu.old.internalUser.dto.InternalUserResponse;
//import ch.teachu.old.util.Assert;
//import ch.teachu.old.util.IStorageAccessService;
//import ch.teachu.old.util.ValidationUtil;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.core.io.Resource;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.UUID;
//
//@Service
//public class InternalUserService extends AbstractSecurityService {
//
//    private final InternalUserRepo internalUserRepo;
//    private final PasswordEncoder passwordEncoder;
//    private final IStorageAccessService storageAccessService;
//
//    private static final String PROFILE_IMAGE_FOLDER = "profileImage";
//
//    public InternalUserService(PasswordEncoder passwordEncoder, AuthRepo authRepo, InternalUserRepo internalUserRepo, @Qualifier("localStorageAccessService") IStorageAccessService storageAccessService) {
//        super(passwordEncoder, authRepo);
//        this.internalUserRepo = internalUserRepo;
//        this.passwordEncoder = passwordEncoder;
//        this.storageAccessService = storageAccessService;
//    }
//
//    public ResponseEntity<MessageResponse> create(CreateUserRequest createUserRequest) {
//        ValidationUtil.checkIfEmailIsValid(createUserRequest.getEmail());
//        ValidationUtil.checkIfPasswordIsValid(createUserRequest.getPassword());
//
//        if (internalUserRepo.existsByEmail(createUserRequest.getEmail())) {
//            throw new InvalidException(createUserRequest.getEmail());
//        }
//
//        internalUserRepo.createUser(createUserRequest.getEmail(), passwordEncoder.encode(createUserRequest.getPassword()), UserRole.ADMIN);
//        return ResponseEntity.ok().body(new MessageResponse("Successfully created user"));
//    }
//
//    public ResponseEntity<InternalUserResponse> getUser(String auth) {
//        UUID userId = authMinRole(auth, UserRole.PARENT).getUserId();
//        return ResponseEntity.ok(internalUserRepo.findById(userId)
//                .orElseThrow(() -> new NotFoundException("User " + userId)));
//    }
//
//    public ResponseEntity<MessageResponse> changeProfile(String auth, ChangeProfileRequest changeProfileRequest) {
//        UUID userId = authMinRole(auth, UserRole.PARENT).getUserId();
//        internalUserRepo.changeProfile(userId, changeProfileRequest);
//        return ResponseEntity.ok(new MessageResponse("Successfully changed profile"));
//    }
//
//    public ResponseEntity<Resource> downloadProfileImage(String auth, UUID userId) {
//        authMinRole(auth, UserRole.PARENT);
//        return ResponseEntity.ok(storageAccessService.load(PROFILE_IMAGE_FOLDER, userId.toString()));
//    }
//
//    public ResponseEntity<MessageResponse> uploadProfileImage(String auth, MultipartFile file) {
//        UUID userId = authMinRole(auth, UserRole.PARENT).getUserId();
//        Assert.ensureNotNull(file, "File must not be null");
//        storageAccessService.save(file, PROFILE_IMAGE_FOLDER, userId.toString());
//        return ResponseEntity.ok(new MessageResponse("Successfully saved profile image"));
//    }
//
//    public ResponseEntity<MessageResponse> deleteProfileImage(String auth) {
//        UUID userId = authMinRole(auth, UserRole.PARENT).getUserId();
//        storageAccessService.delete(PROFILE_IMAGE_FOLDER, userId.toString());
//        return ResponseEntity.ok(new MessageResponse("Successfully deleted profile image"));
//    }
//}
