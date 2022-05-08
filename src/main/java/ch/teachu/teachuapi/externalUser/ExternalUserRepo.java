package ch.teachu.teachuapi.externalUser;

import ch.teachu.teachuapi.externalUser.dto.ExternalUserDTO;
import ch.teachu.teachuapi.parent.AbstractRepo;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static ch.teachu.teachuapi.generated.tables.User.USER;

@Repository
public class ExternalUserRepo extends AbstractRepo {

    public Optional<ExternalUserDTO> findById(UUID userId) {
        return sql().select(USER.EMAIL.as(ExternalUserDTO.EMAIL),
                        USER.ROLE.as(USER.ROLE.as(ExternalUserDTO.ROLE)),
                        USER.FIRST_NAME.as(ExternalUserDTO.FIRST_NAME),
                        USER.LAST_NAME.as(ExternalUserDTO.LAST_NAME),
                        USER.BIRTHDAY.as(ExternalUserDTO.BIRTHDAY),
                        USER.SEX.as(ExternalUserDTO.SEX),
                        USER.CITY.as(ExternalUserDTO.CITY),
                        USER.PROFILE_IMG.as(ExternalUserDTO.PROFILE_IMAGE))
                .from(USER)
                .where(USER.ID.eq(userId))
                .fetchOptionalInto(ExternalUserDTO.class);
    }
}
