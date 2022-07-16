//package ch.teachu.old.externalUser;
//
//import ch.teachu.old.parent.AbstractRepo;
//import ch.teachu.old.externalUser.dto.ExternalUserResponse;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import static ch.teachu.teachuapi.generated.tables.User.USER;
//
//@Repository
//public class ExternalUserRepo extends AbstractRepo {
//
//    public Optional<ExternalUserResponse> findById(UUID userId) {
//        return sql().select(USER.EMAIL.as(ExternalUserResponse.EMAIL),
//                        USER.ID.as(ExternalUserResponse.ID),
//                        USER.ROLE.as(ExternalUserResponse.ROLE),
//                        USER.FIRST_NAME.as(ExternalUserResponse.FIRST_NAME),
//                        USER.LAST_NAME.as(ExternalUserResponse.LAST_NAME),
//                        USER.BIRTHDAY.as(ExternalUserResponse.BIRTHDAY),
//                        USER.SEX.as(ExternalUserResponse.SEX),
//                        USER.CITY.as(ExternalUserResponse.CITY))
//                .from(USER)
//                .where(USER.ID.eq(userId))
//                .fetchOptionalInto(ExternalUserResponse.class);
//    }
//}
