package ch.teachu.teachuapi.sql.generation;

import ch.teachu.teachuapi.enums.UserSex;
import org.jooq.Converter;

public class UserSexConverter implements Converter<String, UserSex> {

    @Override
    public UserSex from(String databaseObject) {
        return UserSex.valueOf(databaseObject.toUpperCase());
    }

    @Override
    public String to(UserSex userObject) {
        return userObject.name().toLowerCase();
    }

    @Override
    public Class<String> fromType() {
        return String.class;
    }

    @Override
    public Class<UserSex> toType() {
        return UserSex.class;
    }
}
