package ch.teachu.teachuapi.internalUser;

import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.errorhandling.NotFoundException;
import ch.teachu.teachuapi.generated.tables.records.UserRecord;
import ch.teachu.teachuapi.internalUser.dto.ChangeProfileRequest;
import ch.teachu.teachuapi.internalUser.dto.InternalUserResponse;
import ch.teachu.teachuapi.parent.AbstractRepo;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static ch.teachu.teachuapi.generated.tables.User.USER;

@Repository
public class InternalUserRepo extends AbstractRepo {

    public boolean existsByEmail(String email) {
        return  sql().fetchExists(sql().selectFrom(USER)
                .where(USER.EMAIL.eq(email)));
    }

    public void createUser(String email, String password, UserRole userRole) {
        sql().insertInto(USER, USER.ID, USER.EMAIL, USER.PASSWORD, USER.ROLE)
                .values(UUID.randomUUID(), email, password, userRole)
                .execute();
    }

    public Optional<InternalUserResponse> findById(UUID userId) {
        return sql().select(USER.EMAIL.as(InternalUserResponse.EMAIL),
                USER.ROLE.as(InternalUserResponse.ROLE),
                USER.FIRST_NAME.as(InternalUserResponse.FIRST_NAME),
                USER.LAST_NAME.as(InternalUserResponse.LAST_NAME),
                USER.BIRTHDAY.as(InternalUserResponse.BIRTHDAY),
                USER.SEX.as(InternalUserResponse.SEX),
                USER.LANGUAGE.as(InternalUserResponse.LANGUAGE),
                USER.DARK_THEME.as(InternalUserResponse.DARK_THEME),
                USER.CITY.as(InternalUserResponse.CITY),
                USER.POSTAL_CODE.as(InternalUserResponse.POSTAL_CODE),
                USER.STREET.as(InternalUserResponse.STREET),
                USER.PHONE.as(InternalUserResponse.PHONE),
                USER.PROFILE_IMG.as(InternalUserResponse.PROFILE_IMAGE))
                .from(USER)
                .where(USER.ID.eq(userId))
                .fetchOptionalInto(InternalUserResponse.class);
    }

    public void changeProfile(UUID userId, ChangeProfileRequest changeProfileRequest) {
        UserRecord user = sql().fetchOptional(USER, USER.ID.eq(userId))
                .orElseThrow(() -> new NotFoundException("User " + userId));
        user.setLanguage(changeProfileRequest.getLanguage());
        user.setDarkTheme(changeProfileRequest.isDarkTheme());
        user.setPhone(changeProfileRequest.getPhone());
        user.setProfileImg(changeProfileRequest.getProfileImage());
        user.store();
    }

    public void changePassword(UUID userId, String password) {
        UserRecord user = sql().fetchOptional(USER, USER.ID.eq(userId))
                .orElseThrow(() -> new NotFoundException("User " + userId));
        user.setPassword(password);
        user.store();
    }
}
