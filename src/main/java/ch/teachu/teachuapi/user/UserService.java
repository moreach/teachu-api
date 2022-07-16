package ch.teachu.teachuapi.user;

import ch.teachu.teachuapi.parent.AbstractService;
import ch.teachu.teachuapi.parent.dtos.MessageResponse;
import ch.teachu.teachuapi.parent.enums.UserLanguage;
import ch.teachu.teachuapi.parent.enums.UserRole;
import ch.teachu.teachuapi.parent.enums.UserSex;
import ch.teachu.teachuapi.user.dtos.ChangeProfileRequest;
import ch.teachu.teachuapi.user.dtos.ExternalUserResponse;
import ch.teachu.teachuapi.user.dtos.InternalUserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService extends AbstractService {
    public ResponseEntity<InternalUserResponse> getInternalUser(String access) {
        // todo check if is same user as access
        // todo get user data

        return ResponseEntity.ok(new InternalUserResponse(
                    "mock@email.ch",
                    UserRole.student,
                    "mockFirstname",
                    "mockLastname",
                    new Date(),
                UserSex.other,
                UserLanguage.english,
                false,
                "mockCity",
                "mockPostalcode",
                "mockStreet",
                "mockPhone"
                )
        );
    }

    public ResponseEntity<ExternalUserResponse> getExternalUser(String access, String userId) {
        authMinRole(access, UserRole.parent);

        // todo get user data by id

        return ResponseEntity.ok(
                new ExternalUserResponse(
                        "mock@email.ch",
                        UserRole.student,
                        "mockFirstname",
                        "mockLastname",
                        new Date(),
                        UserSex.other,
                        "mockCity"
                )
        );
    }

    public ResponseEntity<MessageResponse> changeProfile(String access, ChangeProfileRequest changeProfileRequest) {
        // todo check if is same user as access

        // todo update user

        return ResponseEntity.ok(new MessageResponse("Successfully mocked change profile endpoint"));
    }
}
