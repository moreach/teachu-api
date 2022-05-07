package ch.teachu.teachuapi.sql.generation;

import ch.teachu.teachuapi.enums.UserLanguage;
import org.jooq.Converter;

public class UserLanguageConverter implements Converter<String, UserLanguage> {

    @Override
    public UserLanguage from(String databaseObject) {
        return UserLanguage.valueOf(databaseObject.toUpperCase());
    }

    @Override
    public String to(UserLanguage userObject) {
        return userObject.name().toLowerCase();
    }

    @Override
    public Class<String> fromType() {
        return String.class;
    }

    @Override
    public Class<UserLanguage> toType() {
        return UserLanguage.class;
    }
}
