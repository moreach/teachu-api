package ch.teachu.teachuapi.user;

import ch.teachu.teachuapi.shared.dtos.MessageResponse;
import ch.teachu.teachuapi.user.dtos.ChangeProfileRequest;
import ch.teachu.teachuapi.user.dtos.ExternalUserResponse;
import ch.teachu.teachuapi.user.dtos.InternalUserResponse;
import ch.teachu.teachuapi.user.dtos.UserSyncDataRequest;
import ch.teachu.teachuapi.user.dtos.UserSyncDataResponse;
import ch.teachu.teachuapi.user.dtos.UserSyncTokenRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        return ResponseEntity.ok(userService.getInternalUser(access));
    }

    @Operation(summary = "Load data of external user")
    @GetMapping("/{userId}")
    private ResponseEntity<ExternalUserResponse> getUser(@RequestHeader("access") String access, @PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.getExternalUser(access, userId));
    }

    @Operation(summary = "Change user data and settings of logged in user")
    @PutMapping
    private ResponseEntity<MessageResponse> changeProfile(@RequestHeader("access") String access, @RequestBody ChangeProfileRequest changeProfileRequest) {
        return ResponseEntity.ok(userService.changeProfile(access, changeProfileRequest));
    }

    @Operation(summary = "Upload profile image")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<MessageResponse> createImage(@RequestHeader("access") String access, @RequestParam("file") MultipartFile image) {
        return ResponseEntity.ok(userService.createImage(access, image));
    }

    // system sync
    @Operation(summary = "Check token and get user id")
    @PostMapping("/sync/token")
    private ResponseEntity<UserSyncDataResponse> systemSyncToken(@RequestHeader("apikey") String apikey, @RequestBody UserSyncTokenRequest userSyncTokenRequest) {
        return ResponseEntity.ok(userService.systemSyncToken(apikey, userSyncTokenRequest));
    }

    @Operation(summary = "Update users")
    @PostMapping("/sync/user")
    private ResponseEntity<MessageResponse> systemSyncUsers(@RequestHeader("apikey") String apikey, @RequestBody List<UserSyncDataRequest> userSyncDataRequests) {
        return ResponseEntity.ok(userService.systemSyncUsers(apikey, userSyncDataRequests));
    }
}
