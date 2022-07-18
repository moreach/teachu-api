package ch.teachu.teachuapi.user;

import ch.teachu.teachuapi.shared.AbstractService;
import ch.teachu.teachuapi.shared.dtos.MessageResponse;
import ch.teachu.teachuapi.shared.dtos.SharedDAO;
import ch.teachu.teachuapi.shared.enums.UserLanguage;
import ch.teachu.teachuapi.shared.enums.UserRole;
import ch.teachu.teachuapi.shared.enums.UserSex;
import ch.teachu.teachuapi.shared.errorhandlig.NotFoundException;
import ch.teachu.teachuapi.sql.SQL;
import ch.teachu.teachuapi.user.dtos.ChangeProfileRequest;
import ch.teachu.teachuapi.user.dtos.ExternalUserResponse;
import ch.teachu.teachuapi.user.dtos.InternalUserResponse;
import ch.teachu.teachuapi.user.dtos.UserDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService {
    public ResponseEntity<InternalUserResponse> getInternalUser(String access) {
        SharedDAO sharedDAO = authMinRole(access, UserRole.parent);

        UserDAO userDAO = new UserDAO();

        SQL.select("" +
                        "SELECT email, " +
                        "       role, " +
                        "       first_name, " +
                        "       last_name, " +
                        "       birthday, " +
                        "       sex, " +
                        "       language, " +
                        "       dark_theme, " +
                        "       city, " +
                        "       postal_code, " +
                        "       street, " +
                        "       phone " +
                        "FROM   user " +
                        "WHERE  id = UUID_TO_BIN(-userId) " +
                        "INTO   :email, " +
                        "       :role, " +
                        "       :firstName, " +
                        "       :lastName, " +
                        "       :birthday, " +
                        "       :sex, " +
                        "       :language, " +
                        "       :darkTheme, " +
                        "       :city, " +
                        "       :postalCode, " +
                        "       :street, " +
                        "       :phone ",
                userDAO,
                sharedDAO);

        return ResponseEntity.ok(new InternalUserResponse(
                        userDAO.getEmail(),
                        UserRole.valueOf(userDAO.getRole()),
                        userDAO.getFirstName(),
                        userDAO.getLastName(),
                        userDAO.getBirthday(),
                        UserSex.valueOf(userDAO.getSex()),
                        UserLanguage.valueOf(userDAO.getLanguage()),
                        userDAO.getDarkTheme(),
                        userDAO.getCity(),
                        userDAO.getPostalCode(),
                        userDAO.getStreet(),
                        userDAO.getPhone()
                )
        );
    }

    public ResponseEntity<ExternalUserResponse> getExternalUser(String access, String userId) {
        authMinRole(access, UserRole.parent);

        UserDAO userDAO = new UserDAO();
        userDAO.setUserId(userId);

        SQL.select("" +
                        "SELECT email, " +
                        "       role, " +
                        "       first_name, " +
                        "       last_name, " +
                        "       birthday, " +
                        "       sex, " +
                        "       city " +
                        "FROM   user " +
                        "WHERE  id = UUID_TO_BIN(-userId) " +
                        "AND    active IS TRUE " +
                        "INTO   :email, " +
                        "       :role, " +
                        "       :firstName, " +
                        "       :lastName, " +
                        "       :birthday, " +
                        "       :sex, " +
                        "       :city ",
                userDAO,
                userDAO);

        if (userDAO.getEmail() == null) {
            throw new NotFoundException("User with id " + userId);
        }

        return ResponseEntity.ok(
                new ExternalUserResponse(
                        userDAO.getEmail(),
                        UserRole.valueOf(userDAO.getRole()),
                        userDAO.getFirstName(),
                        userDAO.getLastName(),
                        userDAO.getBirthday(),
                        UserSex.valueOf(userDAO.getSex()),
                        userDAO.getCity()
                )
        );
    }

    public ResponseEntity<MessageResponse> changeProfile(String access, ChangeProfileRequest changeProfileRequest) {
        SharedDAO sharedDAO = authMinRole(access, UserRole.parent);

        UserDAO userDAO = new UserDAO();
        userDAO.setUserId(sharedDAO.getUserId());
        userDAO.setLanguage(changeProfileRequest.getLanguage().name());
        userDAO.setDarkTheme(changeProfileRequest.isDarkTheme());
        userDAO.setPhone(changeProfileRequest.getPhone());

        int count = SQL.update("" +
                        "UPDATE user " +
                        "SET    language = -language, " +
                        "       dark_theme = -darkTheme, " +
                        "       phone = -phone " +
                        "WHERE  id = UUID_TO_BIN(-userId) ",
                userDAO);

        if (count == 0) {
            throw new RuntimeException("Failed to update user");
        }

        return ResponseEntity.ok(new MessageResponse("Successfully changed profile"));
    }
}
