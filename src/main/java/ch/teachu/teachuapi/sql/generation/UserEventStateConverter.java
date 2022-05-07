package ch.teachu.teachuapi.sql.generation;

import ch.teachu.teachuapi.enums.UserEventState;
import org.jooq.Converter;

public class UserEventStateConverter implements Converter<String, UserEventState> {

    @Override
    public UserEventState from(String databaseObject) {
        return UserEventState.valueOf(databaseObject.toUpperCase());
    }

    @Override
    public String to(UserEventState userObject) {
        return userObject.name().toLowerCase();
    }

    @Override
    public Class<String> fromType() {
        return String.class;
    }

    @Override
    public Class<UserEventState> toType() {
        return UserEventState.class;
    }
}
