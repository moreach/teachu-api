package ch.teachu.teachuapi.externalUser;

import ch.teachu.teachuapi.externalUser.dto.ExternalUserDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "User")
@RestController
public class ExternalUserController {

    private final ExternalUserService externalUserService;

    public ExternalUserController(ExternalUserService externalUserService) {
        this.externalUserService = externalUserService;
    }

    @GetMapping("/user/{userId}")
    private ResponseEntity<ExternalUserDTO> getUser(@RequestHeader("auth") String auth, @PathVariable("userId") UUID userId) {
        return externalUserService.getUser(auth, userId);
    }
}
