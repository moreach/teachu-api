package ch.teachu.teachuapi.parent;

import ch.teachu.teachuapi.daos.AuthDAO;
import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.errorhandling.UnauthorizedException;
import ch.teachu.teachuapi.generated.tables.Token;
import ch.teachu.teachuapi.generated.tables.User;

import java.time.LocalDateTime;
import java.util.Optional;

import static ch.teachu.teachuapi.sql.Sql.SQL;

public abstract class AbstractService {
	
	protected AuthDAO authenticate(String tokenAccess, UserRole requiredRole) {
		AuthDAO auth = loadAuth(tokenAccess)
				.orElseThrow(() -> new UnauthorizedException("Token not found: " + tokenAccess));

		ensureExpired(auth.getRefreshExpires());
		ensureExpired(auth.getAccessExpires());
		ensureRolePermitted(auth, requiredRole);
		return auth;
	}

	protected Optional<AuthDAO> loadAuth(String tokenAccess) {
		return Optional.ofNullable(
				SQL.select(Token.TOKEN.ACCESS_EXPIRES.as(AuthDAO.ACCESS_EXPIRES),
								Token.TOKEN.REFRESH_EXPIRES.as(AuthDAO.REFRESH_EXPIRES),
								User.USER.ID.as(AuthDAO.USER_ID),
								User.USER.ROLE.as(AuthDAO.ROLE))
						.from(Token.TOKEN)
						.join(User.USER)
						.on(Token.TOKEN.USER_ID.eq(User.USER.ID))
						.where(Token.TOKEN.ACCESS.eq(tokenAccess))
						.fetchOneInto(AuthDAO.class)
		);
	}

	protected void ensureExpired(LocalDateTime expires) {
		if (expires.isBefore(LocalDateTime.now())) {
			throw new UnauthorizedException("Token expired");
		}
	}

	protected void ensureRolePermitted(AuthDAO auth, UserRole requiredRole) {
		if (auth.getRole().getLevel() < requiredRole.getLevel()) {
			throw new UnauthorizedException("Not permitted to perform this action. Required at least role: " + requiredRole + ". Your role: " + auth.getRole());
		}
	}
}
