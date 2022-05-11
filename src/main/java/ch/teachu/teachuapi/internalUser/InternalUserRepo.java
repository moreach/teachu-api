package ch.teachu.teachuapi.internalUser;

import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.generated.tables.records.UserRecord;
import ch.teachu.teachuapi.internalUser.dto.ChangeProfileDTO;
import ch.teachu.teachuapi.internalUser.dto.PersonalUserDTO;
import ch.teachu.teachuapi.parent.AbstractRepo;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static ch.teachu.teachuapi.generated.tables.User.USER;

@Repository
public class InternalUserRepo extends AbstractRepo {

    public boolean existsByEmail(String email) {
        Integer matchCount = sql().selectCount()
                .from(USER)
                .where(USER.EMAIL.eq(email))
                .fetchOneInto(Integer.class);

        return matchCount == null || matchCount > 0;
    }

    public void createUser(String email, String password, UserRole userRole) {
        sql().insertInto(USER, USER.ID, USER.EMAIL, USER.PASSWORD, USER.ROLE)
                .values(UUID.randomUUID(), email, password, userRole)
                .execute();
    }

    public Optional<PersonalUserDTO> findById(UUID userId) {
        return sql().select(USER.EMAIL.as(PersonalUserDTO.EMAIL),
                USER.ROLE.as(PersonalUserDTO.ROLE),
                USER.FIRST_NAME.as(PersonalUserDTO.FIRST_NAME),
                USER.LAST_NAME.as(PersonalUserDTO.LAST_NAME),
                USER.BIRTHDAY.as(PersonalUserDTO.BIRTHDAY),
                USER.SEX.as(PersonalUserDTO.SEX),
                USER.LANGUAGE.as(PersonalUserDTO.LANGUAGE),
                USER.DARK_THEME.as(PersonalUserDTO.DARK_THEME),
                USER.CITY.as(PersonalUserDTO.CITY),
                USER.POSTAL_CODE.as(PersonalUserDTO.POSTAL_CODE),
                USER.STREET.as(PersonalUserDTO.STREET),
                USER.PHONE.as(PersonalUserDTO.PHONE),
                USER.PROFILE_IMG.as(PersonalUserDTO.PROFILE_IMAGE))
                .from(USER)
                .where(USER.ID.eq(userId))
                .fetchOptionalInto(PersonalUserDTO.class);
    }

    public void changeProfile(UUID userId, ChangeProfileDTO changeProfileDTO) {
        UserRecord user = sql().fetchOne(USER, USER.ID.eq(userId));
        assert user != null;
        user.setLanguage(changeProfileDTO.getLanguage());
        user.setDarkTheme(changeProfileDTO.isDarkTheme());
        user.setPhone(changeProfileDTO.getPhone());
        user.setProfileImg(changeProfileDTO.getProfileImage());
        user.setNotes(changeProfileDTO.getNotes());
        user.store();
    }
}
