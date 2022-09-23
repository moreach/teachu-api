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
import ch.teachu.teachuapi.shared.errorhandlig.UnauthorizedException;
import ch.teachu.teachuapi.shared.properties.SyncProperties;
import ch.teachu.teachuapi.sql.SQL;
import ch.teachu.teachuapi.user.dtos.ChangeProfileRequest;
import ch.teachu.teachuapi.user.dtos.ExternalUserResponse;
import ch.teachu.teachuapi.user.dtos.InternalUserResponse;
import ch.teachu.teachuapi.user.dtos.UserDAO;
import ch.teachu.teachuapi.user.dtos.UserSyncDataRequest;
import ch.teachu.teachuapi.user.dtos.UserSyncDataResponse;
import ch.teachu.teachuapi.user.dtos.UserSyncTokenRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService extends AbstractService {

    private final ImageService imageService;
    private final SyncProperties syncProperties;

    public UserService(ImageService imageService, SyncProperties syncProperties) {
        this.imageService = imageService;
        this.syncProperties = syncProperties;
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
                userDAO.getSex() == null ? null : UserSex.valueOf(userDAO.getSex()),
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

        sendSyncDataToLearnz();

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

        sendSyncDataToLearnz();

        return new MessageResponse("Successfully uploaded profile image");
    }

    public UserSyncDataResponse systemSyncToken(String apikey, UserSyncTokenRequest userSyncTokenRequest) {
        if (!syncProperties.getApiKey().equals(apikey)) {
            throw new UnauthorizedException("api key mismatch");
        }

        SharedDAO sharedDAO = authMinRole(userSyncTokenRequest.getAccessToken(), UserRole.parent);

        return new UserSyncDataResponse(sharedDAO.getUserId());
    }

    public MessageResponse systemSyncUsers(String apikey, List<UserSyncDataRequest> userSyncDataRequests) {
        if (!syncProperties.getApiKey().equals(apikey)) {
            throw new UnauthorizedException("api key mismatch");
        }

        for (UserSyncDataRequest userSyncDataRequest : userSyncDataRequests) {
            try {
                UserDAO userDAO = new UserDAO();
                userDAO.setUserId(userSyncDataRequest.getUserId());
                userDAO.setFirstName(userSyncDataRequest.getFirstname());
                userDAO.setLastName(userSyncDataRequest.getLastname());
                userDAO.setBirthday(new SimpleDateFormat("dd-MM-yyyy").parse(userSyncDataRequest.getBirthdate()));
                userDAO.setImageId(userSyncDataRequest.getProfileImageId());

                int count = SQL.update("" +
                        "UPDATE user " +
                        "SET    first_name = -firstName " +
                        "       last_name = -lastName " +
                        "       birthday = -birthday " +
                        "       img = UUID_TO_BIN(-imageId) " +
                        "WHERE  id = UUID_TO_BIN(-userId)",
                    userDAO);

                if (count == 0) {
                    throw new RuntimeException("Failed to update user " + userSyncDataRequest.getUserId());
                }
            } catch (ParseException e) {
                throw new RuntimeException("Failed to parse date form user " + userSyncDataRequest.getUserId());
            }
        }

        return new MessageResponse("Successfully updated users");
    }

    private void sendSyncDataToLearnz() {
        List<UserDAO> userDAOs = new ArrayList<>();

        SQL.select("" +
                "SELECT BIN_TO_UUID(id)" +
                "       first_name, " +
                "       last_name, " +
                "       birthday, " +
                "       BIN_TO_UUID(img)" +
                "FROM   user " +
                "WHERE  active IS TRUE " +
                "INTO   :userId, " +
                "       :firstName, " +
                "       :lastName, " +
                "       :birthday, " +
                "       :imageId ",
            userDAOs,
            UserDAO.class);

        List<UserSyncDataRequest> userSyncDataRequests = new ArrayList<>();

        for (UserDAO userDAO : userDAOs) {
            UserSyncDataRequest userSyncDataRequest = new UserSyncDataRequest();
            userSyncDataRequest.setUserId(userDAO.getUserId());
            userSyncDataRequest.setFirstname(userDAO.getFirstName());
            userSyncDataRequest.setLastname(userDAO.getLastName());
            userSyncDataRequest.setBirthdate(new SimpleDateFormat("dd-MM-yyyy").format(userDAO.getBirthday()));
            userSyncDataRequest.setProfileImageId(userDAO.getImageId());

            userSyncDataRequests.add(userSyncDataRequest);
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<UserSyncDataRequest>> httpEntity = new HttpEntity(
            userSyncDataRequests,
            httpHeaders
        );

        ResponseEntity<Void> responseEntity = restTemplate.exchange(
            "http://localhost:7039/api/UserSynchronization",
            HttpMethod.POST,
            httpEntity,
            Void.class);


        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to sync systems");
        }
    }
}
