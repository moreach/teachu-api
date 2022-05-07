package ch.teachu.teachuapi.sql.generation;

import ch.teachu.teachuapi.enums.Role;
import org.jooq.Converter;

public class RoleConverter implements Converter<String, Role> {

    @Override
    public Role from(String databaseObject) {
        return Role.valueOf(databaseObject.toUpperCase());
    }

    @Override
    public String to(Role userObject) {
        return userObject.name().toLowerCase();
    }

    @Override
    public Class<String> fromType() {
        return String.class;
    }

    @Override
    public Class<Role> toType() {
        return Role.class;
    }
}
