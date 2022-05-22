package ch.teachu.teachuapi.auth;

import ch.teachu.teachuapi.auth.daos.AuthUserDAO;
import ch.teachu.teachuapi.generated.tables.records.TokenRecord;
import ch.teachu.teachuapi.parent.AbstractRepo;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static ch.teachu.teachuapi.generated.tables.Token.TOKEN;
import static ch.teachu.teachuapi.generated.tables.User.USER;

@Repository
public class AuthRepo extends AbstractRepo {
    public Optional<AuthUserDAO> findAuthUserByEmail(String email) {
        return Optional.ofNullable(
                sql().select(USER.ID.as(AuthUserDAO.ID),
                        USER.PASSWORD.as(AuthUserDAO.PASSWORD))
                        .from(USER)
                        .where(USER.EMAIL.eq(email))
                        .fetchOneInto(AuthUserDAO.class)
        );
    }

    public TokenRecord storeToken(String access, String refresh, LocalDateTime accessExpires, LocalDateTime refreshExpires, UUID userId) {
        return storeTokenInternal(access, refresh, accessExpires, refreshExpires, userId);
    }


    protected TokenRecord storeTokenInternal(String access, String refresh, LocalDateTime accessExpires, LocalDateTime refreshExpires, UUID userId) {
        TokenRecord token = sql().newRecord(TOKEN);
        token.setAccess(access);
        token.setRefresh(refresh);
        token.setAccessExpires(accessExpires);
        token.setRefreshExpires(refreshExpires);
        token.setUserId(userId);
        token.store();
        return token;
    }

    public int deleteExpiredTokens() {
        return sql().deleteFrom(TOKEN)
                .where("refresh_expires < current_timestamp")
                .execute();
    }

    public Optional<TokenRecord> findTokenByRefresh(String refresh) {
        return Optional.ofNullable(
                sql().selectFrom(TOKEN)
                .where(TOKEN.REFRESH.eq(refresh))
                .fetchOne()
        );
    }

    public int deleteTokenByRefresh(String refresh) {
        return sql().deleteFrom(TOKEN)
                .where(TOKEN.REFRESH.eq(refresh))
                .execute();
    }

    public void deleteTokensByUserId(UUID userId) {
        sql().deleteFrom(TOKEN).where(TOKEN.USER_ID.eq(userId)).execute();
    }
}
