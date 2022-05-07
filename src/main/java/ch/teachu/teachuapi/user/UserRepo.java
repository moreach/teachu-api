package ch.teachu.teachuapi.user;

import ch.teachu.teachuapi.enums.Role;
import ch.teachu.teachuapi.parent.AbstractRepo;
import ch.teachu.techuapi.generated.tables.User;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRepo extends AbstractRepo {

    public boolean existsByEmail(String email) {
        Integer matchCount = sql().selectCount()
                .from(User.USER)
                .where(User.USER.EMAIL.eq(email))
                .fetchOneInto(Integer.class);

        return matchCount == null || matchCount > 0;
    }

    public void createUser(String email, String password, Role role) {
        sql().insertInto(User.USER, User.USER.ID, User.USER.EMAIL, User.USER.PASSWORD, User.USER.ROLE)
                .values(UUID.randomUUID(), email, password, role)
                .execute();
    }
}
