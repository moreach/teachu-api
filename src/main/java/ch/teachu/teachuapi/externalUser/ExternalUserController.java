package ch.teachu.teachuapi.externalUser;

import ch.teachu.teachuapi.externalUser.dto.ExternalUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "User")
@RequestMapping("/user")
@RestController
public class ExternalUserController {

    private final ExternalUserService externalUserService;

    public ExternalUserController(ExternalUserService externalUserService) {
        this.externalUserService = externalUserService;
    }

    @Operation(summary = "Load data of external user")
    @GetMapping("/{userId}")
    private ResponseEntity<ExternalUserResponse> getUser(@RequestHeader("auth") String auth, @PathVariable("userId") UUID userId) {
        return externalUserService.getUser(auth, userId);
    }
}
