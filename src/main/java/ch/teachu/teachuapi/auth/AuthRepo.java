package ch.teachu.teachuapi.auth;

import ch.teachu.teachuapi.auth.daos.AuthUserDao;
import ch.teachu.teachuapi.parent.AbstractRepo;
import ch.teachu.techuapi.generated.tables.Token;
import ch.teachu.techuapi.generated.tables.User;
import ch.teachu.techuapi.generated.tables.records.TokenRecord;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AuthRepo extends AbstractRepo {
    public Optional<AuthUserDao> findAuthUserByEmail(String email) {
        return Optional.ofNullable(
                sql().select(User.USER.ID.as(AuthUserDao.ID),
                        User.USER.PASSWORD.as(AuthUserDao.PASSWORD))
                        .from(User.USER)
                        .where(User.USER.EMAIL.eq(email))
                        .fetchOneInto(AuthUserDao.class)
        );
    }

    public TokenRecord storeToken(String access, String refresh, LocalDateTime accessExpires, LocalDateTime refreshExpires, UUID userId) {
        deleteOldToken(userId);
        return storeTokenInternal(access, refresh, accessExpires, refreshExpires, userId);
    }

    protected void deleteOldToken(UUID userId) {
        sql().deleteFrom(Token.TOKEN)
                .where(Token.TOKEN.USER_ID.eq(userId))
                .execute();
    }

    protected TokenRecord storeTokenInternal(String access, String refresh, LocalDateTime accessExpires, LocalDateTime refreshExpires, UUID userId) {
        TokenRecord token = sql().newRecord(Token.TOKEN);
        token.setAccess(access);
        token.setRefresh(refresh);
        token.setAccessExpires(accessExpires);
        token.setRefreshExpires(refreshExpires);
        token.setUserId(userId);
        token.store();
        return token;
    }

    public int deleteExpiredTokens() {
        return sql().deleteFrom(Token.TOKEN)
                .where("refresh_expires < current_timestamp")
                .execute();
    }

    public Optional<TokenRecord> findTokenByRefresh(String refresh) {
        return Optional.ofNullable(
                sql().selectFrom(Token.TOKEN)
                .where(Token.TOKEN.REFRESH.eq(refresh))
                .fetchOne()
        );
    }

    public int deleteTokenByRefresh(String refresh) {
        return sql().deleteFrom(Token.TOKEN)
                .where(Token.TOKEN.REFRESH.eq(refresh))
                .execute();
    }
}
