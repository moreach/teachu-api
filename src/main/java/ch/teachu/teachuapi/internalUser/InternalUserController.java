package ch.teachu.teachuapi.internalUser;

import ch.teachu.teachuapi.dtos.MessageResponse;
import ch.teachu.teachuapi.internalUser.dto.ChangeProfileRequest;
import ch.teachu.teachuapi.internalUser.dto.CreateUserRequest;
import ch.teachu.teachuapi.internalUser.dto.InternalUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Tag(name = "User")
@RequestMapping("/user")
@RestController
public class InternalUserController {

    private final InternalUserService internalUserService;

    public InternalUserController(InternalUserService internalUserService) {
        this.internalUserService = internalUserService;
    }

    @Operation(summary = "User data and settings of logged in user")
    @GetMapping
    private ResponseEntity<InternalUserResponse> getUser(@RequestHeader("auth") String auth) {
        return internalUserService.getUser(auth);
    }

    @Operation(summary = "Download the profile image")
    @GetMapping("/profileImage/{userId}")
    public ResponseEntity<Resource> downloadProfileImage(@RequestHeader("auth") String auth, @PathVariable UUID userId) {
        return internalUserService.downloadProfileImage(auth, userId);
    }

    // TODO remove
    @Operation(summary = "Create user for testing purposes")
    @PostMapping
    private ResponseEntity<MessageResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        return internalUserService.create(createUserRequest);
    }

    @Operation(summary = "Change user data and settings of logged in user")
    @PutMapping
    private ResponseEntity<MessageResponse> changeProfile(@RequestHeader("auth") String auth, @RequestBody ChangeProfileRequest changeProfileRequest) {
        return internalUserService.changeProfile(auth, changeProfileRequest);
    }

    @Operation(summary = "Upload profile image")
    @PutMapping(value = "/profileImage", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    private ResponseEntity<MessageResponse> uploadProfileImage(@RequestHeader("auth") String auth, @RequestBody MultipartFile file) {
        return internalUserService.uploadProfileImage(auth, file);
    }

    @Operation(summary = "Delete the profile image")
    @DeleteMapping("/profileImage")
    public ResponseEntity<MessageResponse> deleteProfileImage(@RequestHeader("auth") String auth) {
        return internalUserService.deleteProfileImage(auth);
    }
}
