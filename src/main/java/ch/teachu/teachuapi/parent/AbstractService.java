package ch.teachu.teachuapi.parent;

import ch.teachu.teachuapi.daos.AuthDAO;
import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.errorhandling.UnauthorizedException;
import ch.teachu.teachuapi.generated.tables.Token;
import ch.teachu.teachuapi.generated.tables.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static ch.teachu.teachuapi.sql.Sql.SQL;

public abstract class AbstractService {
	
	protected AuthDAO authMinRole(String tokenAccess, UserRole minRole) {
		AuthDAO auth = loadAndCheckAuth(tokenAccess);
		ensureRolePermittedMin(auth, minRole);
		return auth;
	}

	protected AuthDAO authExactRole(String tokenAccess, UserRole requiredRole) {
		AuthDAO auth = loadAndCheckAuth(tokenAccess);
		ensureRolePermittedExact(auth, requiredRole);
		return auth;
	}

	protected AuthDAO loadAndCheckAuth(String tokenAccess) {
		AuthDAO auth = loadAuth(tokenAccess)
				.orElseThrow(() -> new UnauthorizedException("Token not found: " + tokenAccess));

		ensureExpired(auth.getRefreshExpires());
		ensureExpired(auth.getAccessExpires());
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

	protected void ensureRolePermittedMin(AuthDAO auth, UserRole minRole) {
		if (auth.getRole().getLevel() < minRole.getLevel()) {
			throwUnauthorizedMin(auth.getRole(), minRole);
		}
	}

	protected void throwUnauthorizedMin(UserRole actualRole, UserRole minRole) {
		throw new UnauthorizedException("Not permitted to perform this action. Required at least role: " + minRole + ". Your role: " + actualRole);
	}

	protected void ensureRolePermittedExact(AuthDAO auth, UserRole requiredRole) {
		if (auth.getRole() != requiredRole) {
			throwUnauthorizedExact(auth.getRole(), requiredRole);
		}
	}

	protected void throwUnauthorizedExact(UserRole actualRole, UserRole... requiredRoles) {
		if (requiredRoles.length == 1) {
			throw new UnauthorizedException("Not permitted to perform this action. Required role: " + requiredRoles[0] + ". Your role: " + actualRole);
		}
		throw new UnauthorizedException("Not permitted to perform this action. You should be one of: " + Arrays.toString(requiredRoles) + ". Your role: " + actualRole);
	}
}
