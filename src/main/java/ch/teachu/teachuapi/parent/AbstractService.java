package ch.teachu.teachuapi.parent;

import ch.teachu.teachuapi.daos.AuthDao;
import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.errorhandling.UnauthorizedException;
import ch.teachu.techuapi.generated.tables.Token;
import ch.teachu.techuapi.generated.tables.User;

import java.time.LocalDateTime;
import java.util.Optional;

import static ch.teachu.teachuapi.sql.Sql.SQL;

public abstract class AbstractService {
	
	protected void authenticate(String tokenAccess, UserRole requiredRole) {
		AuthDao auth = loadAuth(tokenAccess)
				.orElseThrow(() -> new UnauthorizedException("Token not found: " + tokenAccess));

		ensureExpired(auth.getRefreshExpires());
		ensureExpired(auth.getAccessExpires());
		ensureRolePermitted(auth, requiredRole);
	}

	protected Optional<AuthDao> loadAuth(String tokenAccess) {
		return Optional.ofNullable(
				SQL.select(Token.TOKEN.ACCESS_EXPIRES.as(AuthDao.ACCESS_EXPIRES),
								Token.TOKEN.REFRESH_EXPIRES.as(AuthDao.REFRESH_EXPIRES),
								User.USER.ROLE.as(AuthDao.ROLE))
						.from(Token.TOKEN)
						.join(User.USER)
						.on(Token.TOKEN.USER_ID.eq(User.USER.ID))
						.where(Token.TOKEN.ACCESS.eq(tokenAccess))
						.fetchOneInto(AuthDao.class)
		);
	}

	protected void ensureExpired(LocalDateTime expires) {
		if (expires.isBefore(LocalDateTime.now())) {
			throw new UnauthorizedException("Token expired");
		}
	}

	protected void ensureRolePermitted(AuthDao auth, UserRole requiredRole) {
		if (auth.getRole().getLevel() < requiredRole.getLevel()) {
			throw new UnauthorizedException("Not permitted to perform this action. Required at least role: " + requiredRole + ". Your role: " + auth.getRole());
		}
	}
}
