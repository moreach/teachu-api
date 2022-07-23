package ch.teachu.teachuapi.authentication;

import ch.teachu.teachuapi.authentication.dtos.*;
import ch.teachu.teachuapi.shared.dtos.MessageResponse;
import ch.teachu.teachuapi.shared.errorhandlig.InvalidException;
import ch.teachu.teachuapi.shared.errorhandlig.NotFoundException;
import ch.teachu.teachuapi.shared.errorhandlig.UnauthorizedException;
import ch.teachu.teachuapi.shared.properties.SecurityProperties;
import ch.teachu.teachuapi.shared.util.ValidationUtil;
import ch.teachu.teachuapi.sql.SQL;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthService.class);

    private final PasswordEncoder passwordEncoder;
    private final SecurityProperties securityProperties;

    public AuthService(PasswordEncoder passwordEncoder, SecurityProperties securityProperties) {
        this.passwordEncoder = passwordEncoder;
        this.securityProperties = securityProperties;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        UserDAO userDAO = new UserDAO();

        SQL.select("" +
                        "SELECT BIN_TO_UUID(id), " +
                        "       password " +
                        "FROM   user " +
                        "WHERE  email = -email " +
                        "AND    active IS TRUE" +
                        "INTO   :id, " +
                        "       :password ",
                userDAO,
                loginRequest);

        if (userDAO.getId() == null) {
            throw new NotFoundException("Email " + loginRequest.getEmail());
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), userDAO.getPassword())) {
            throw new InvalidException("Password for email " + loginRequest.getEmail());
        }

        userDAO.setAccess(RandomStringUtils.randomAlphanumeric(10));
        userDAO.setAccessExpires(calculateAccessExpires());

        userDAO.setRefresh(RandomStringUtils.randomAlphanumeric(10));
        userDAO.setRefreshExpires(calculateRefreshExpires());

        int count = SQL.insert("" +
                        "INSERT INTO token ( " +
                        "       user_id, " +
                        "       access, " +
                        "       refresh, " +
                        "       access_expires, " +
                        "       refresh_expires) " +
                        "VALUES ( " +
                        "       UUID_TO_BIN(-id), " +
                        "       -access, " +
                        "       -refresh, " +
                        "       -accessExpires, " +
                        "       -refreshExpires) ",
                userDAO);

        if (count == 0) {
            throw new RuntimeException("Failed to store tokens");
        }

        count = SQL.update("" +
                        "UPDATE user " +
                        "SET    last_login = now() " +
                        "WHERE  id = UUID_TO_BIN(-id) ",
                userDAO);

        if (count == 0) {
            throw new RuntimeException("Failed to update user");
        }

        return new LoginResponse(
                userDAO.getAccess(),
                userDAO.getRefresh(),
                userDAO.getAccessExpires()
        );
    }

    public RefreshResponse refresh(RefreshRequest refreshRequest) {
        UserDAO userDAO = new UserDAO();

        SQL.select("" +
                        "SELECT refresh " +
                        "FROM   token t " +
                        "INNER JOIN user u ON t.user_id = u.id " +
                        "WHERE  refresh_expires > now() " +
                        "AND    u.active IS TRUE " +
                        "AND    refresh = -refresh " +
                        "INTO   :refresh ",
                userDAO,
                refreshRequest);

        if (userDAO.getRefresh() == null) {
            throw new UnauthorizedException("Token doesn't exist ");
        }

        userDAO.setAccess(RandomStringUtils.randomAlphanumeric(10));
        userDAO.setAccessExpires(calculateAccessExpires());

        int count = SQL.update("" +
                        "UPDATE token " +
                        "SET    access = -access, " +
                        "       access_expires = -accessExpires " +
                        "WHERE  refresh = -refresh ",
                userDAO);

        if (count == 0) {
            throw new RuntimeException("Failed to update Token");
        }

        return new RefreshResponse(
                userDAO.getAccess(),
                userDAO.getAccessExpires()
        );
    }

    public LoginResponse changePassword(ChangePasswordRequest changePasswordRequest) {
        UserDAO userDAO = new UserDAO();

        SQL.select("" +
                        "SELECT BIN_TO_UUID(id), " +
                        "       password " +
                        "FROM   user " +
                        "WHERE  email = -email " +
                        "AND    active IS TRUE" +
                        "INTO   :id, " +
                        "       :password ",
                userDAO,
                changePasswordRequest);

        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), userDAO.getPassword())) {
            throw new InvalidException("Password for email " + changePasswordRequest.getEmail());
        }

        ValidationUtil.checkIfPasswordIsValid(changePasswordRequest.getNewPassword());

        userDAO.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));

        int count = SQL.update("" +
                        "UPDATE user " +
                        "SET    password = -password " +
                        "WHERE  id = UUID_TO_BIN(-id) ",
                userDAO);

        if (count == 0) {
            throw new RuntimeException("Failed to update user");
        }

        SQL.delete("" +
                        "DELETE FROM token " +
                        "WHERE  user_id = UUID_TO_BIN(-id) ",
                userDAO);

        return login(new LoginRequest(
                        changePasswordRequest.getEmail(),
                        changePasswordRequest.getNewPassword()
                )
        );
    }

    public MessageResponse logout(LogoutRequest logoutRequest) {
        int count = SQL.delete("" +
                        "DELETE FROM token " +
                        "WHERE  refresh = -refresh ",
                logoutRequest);

        if (count == 0) {
            throw new RuntimeException("Failed to logout of sessions");
        }

        return new MessageResponse("Successfully logged out");
    }

    public void deleteExpiredTokens() {

        int count = SQL.delete("" +
                        "DELETE FROM token " +
                        "WHERE refresh_expires < now()",
                null);

        LOG.info("deleted " + count + " expired tokens");
    }

    private Date calculateAccessExpires() {
        return DateUtils.addHours(new Date(), securityProperties.getAccessExpiresInHours());
    }

    private Date calculateRefreshExpires() {
        return DateUtils.addHours(new Date(), securityProperties.getRefreshExpiresInHours());
    }
}
