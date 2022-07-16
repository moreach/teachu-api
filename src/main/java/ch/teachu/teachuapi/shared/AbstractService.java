package ch.teachu.teachuapi.shared;

import ch.teachu.teachuapi.shared.dtos.AccessTokenDTO;
import ch.teachu.teachuapi.shared.dtos.RoleDTO;
import ch.teachu.teachuapi.shared.enums.UserRole;
import ch.teachu.teachuapi.shared.errorhandlig.UnauthorizedException;
import ch.teachu.teachuapi.sql.SQL;

public abstract class AbstractService {

    protected void authMinRole(String accessToken, UserRole minRole) {
        UserRole userRole = loadAuth(accessToken);

        if (userRole.getLevel() < minRole.getLevel()) {
            throw new UnauthorizedException("Not permitted to perform this action. Required at least role: " + minRole + ". Your role: " + userRole);
        }
    }

    protected void authExactRole(String accessToken, UserRole requiredRole) {
        UserRole userRole = loadAuth(accessToken);


        if (userRole != requiredRole) {
            throw new UnauthorizedException("Not permitted to perform this action. Required role: " + requiredRole + ". Your role: " + userRole);
        }
    }

    private UserRole loadAuth(String accessToken) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO(accessToken);
        RoleDTO roleDTO = new RoleDTO();

        SQL.select("" +
                        "SELECT	u.role " +
                        "FROM 	user u " +
                        "JOIN 	token t ON u.id = t.user_id " +
                        "WHERE 	t.access = -accessToken " +
                        "AND 	t.access_expires > current_timestamp " +
                        "AND 	u.active IS TRUE " +
                        "INTO 	:role",
                roleDTO,
                accessTokenDTO);

        return UserRole.valueOf(roleDTO.getRole());
    }
}
