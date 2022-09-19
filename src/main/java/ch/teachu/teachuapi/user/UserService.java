package ch.teachu.teachuapi.user;

import ch.teachu.teachuapi.image.ImageService;
import ch.teachu.teachuapi.image.dtos.ImageResponse;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService extends AbstractService {

    private final ImageService imageService;

    public UserService(ImageService imageService) {
        this.imageService = imageService;
    }

    public InternalUserResponse getInternalUser(String access) {
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
                        "       phone, " +
                        "       BIN_TO_UUID(img) " +
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
                        "       :phone, " +
                        "       :imageId ",
                userDAO,
                sharedDAO);

        return new InternalUserResponse(
                sharedDAO.getUserId(),
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
                userDAO.getPhone(),
                userDAO.getImageId()
        );
    }

    public ExternalUserResponse getExternalUser(String access, String userId) {
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
                        "       city, " +
                        "       BIN_TO_UUID(img) " +
                        "FROM   user " +
                        "WHERE  id = UUID_TO_BIN(-userId) " +
                        "AND    active IS TRUE " +
                        "INTO   :email, " +
                        "       :role, " +
                        "       :firstName, " +
                        "       :lastName, " +
                        "       :birthday, " +
                        "       :sex, " +
                        "       :city, " +
                        "       :imageId ",
                userDAO,
                userDAO);

        if (userDAO.getEmail() == null) {
            throw new NotFoundException("User with id " + userId);
        }

        return new ExternalUserResponse(
                userId,
                userDAO.getEmail(),
                UserRole.valueOf(userDAO.getRole()),
                userDAO.getFirstName(),
                userDAO.getLastName(),
                userDAO.getBirthday(),
                UserSex.valueOf(userDAO.getSex()),
                userDAO.getCity(),
                userDAO.getImageId()
        );
    }

    public MessageResponse changeProfile(String access, ChangeProfileRequest changeProfileRequest) {
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

        return new MessageResponse("Successfully changed profile");
    }

    public MessageResponse createImage(String access, MultipartFile image) {
        SharedDAO sharedDAO = authMinRole(access, UserRole.parent);

        ImageResponse imageResponse = imageService.createImage(access, image);

        UserDAO userDAO = new UserDAO();
        userDAO.setUserId(sharedDAO.getUserId());
        userDAO.setImageId(imageResponse.getId());

        int count = SQL.update("" +
                        "UPDATE user " +
                        "SET    img = UUID_TO_BIN(-imageId) " +
                        "WHERE  id = UUID_TO_BIN(-userId) ",
                userDAO);

        if (count == 0) {
            throw new RuntimeException("Failed to upload profile image");
        }

        return new MessageResponse("Successfully uploaded profile image");
    }
}
