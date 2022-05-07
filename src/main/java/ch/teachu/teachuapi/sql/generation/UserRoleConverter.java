package ch.teachu.teachuapi.sql.generation;

import ch.teachu.teachuapi.enums.UserRole;
import org.jooq.Converter;

public class UserRoleConverter implements Converter<String, UserRole> {

    @Override
    public UserRole from(String databaseObject) {
        return UserRole.valueOf(databaseObject.toUpperCase());
    }

    @Override
    public String to(UserRole userObject) {
        return userObject.name().toLowerCase();
    }

    @Override
    public Class<String> fromType() {
        return String.class;
    }

    @Override
    public Class<UserRole> toType() {
        return UserRole.class;
    }
}
