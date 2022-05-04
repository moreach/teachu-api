package ch.teachu.teachuapi.sql.generation;

import ch.teachu.teachuapi.enums.Language;
import org.jooq.Converter;

public class LanguageConverter implements Converter<String, Language> {

    @Override
    public Language from(String databaseObject) {
        return Language.valueOf(databaseObject);
    }

    @Override
    public String to(Language userObject) {
        return userObject.name();
    }

    @Override
    public Class<String> fromType() {
        return String.class;
    }

    @Override
    public Class<Language> toType() {
        return Language.class;
    }
}
