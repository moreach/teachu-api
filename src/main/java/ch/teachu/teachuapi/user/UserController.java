package ch.teachu.teachuapi.user;

import ch.teachu.teachuapi.shared.dtos.MessageResponse;
import ch.teachu.teachuapi.user.dtos.ChangeProfileRequest;
import ch.teachu.teachuapi.user.dtos.ExternalUserResponse;
import ch.teachu.teachuapi.user.dtos.InternalUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User")
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "User data and settings of logged in user")
    @GetMapping
    private ResponseEntity<InternalUserResponse> getUser(@RequestHeader("access") String access) {
        return userService.getInternalUser(access);
    }

    @Operation(summary = "Load data of external user")
    @GetMapping("/{userId}")
    private ResponseEntity<ExternalUserResponse> getUser(@RequestHeader("access") String access, @PathVariable("userId") String userId) {
        return userService.getExternalUser(access, userId);
    }

    @Operation(summary = "Change user data and settings of logged in user")
    @PutMapping
    private ResponseEntity<MessageResponse> changeProfile(@RequestHeader("access") String access, @RequestBody ChangeProfileRequest changeProfileRequest) {
        return userService.changeProfile(access, changeProfileRequest);
    }

    // todo to be implemented
//    @Operation(summary = "Download the profile image")
//    @GetMapping("/profileImage/{userId}")
//    public ResponseEntity<Resource> downloadProfileImage(@RequestHeader("auth") String auth, @PathVariable UUID userId) {
//        return internalUserService.downloadProfileImage(auth, userId);
//    }

//    @Operation(summary = "Upload profile image")
//    @PutMapping(value = "/profileImage", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    private ResponseEntity<MessageResponse> uploadProfileImage(@RequestHeader("auth") String auth, @RequestBody MultipartFile file) {
//        return internalUserService.uploadProfileImage(auth, file);
//    }
//
//    @Operation(summary = "Delete the profile image")
//    @DeleteMapping("/profileImage")
//    public ResponseEntity<MessageResponse> deleteProfileImage(@RequestHeader("auth") String auth) {
//        return internalUserService.deleteProfileImage(auth);
//    }
}
