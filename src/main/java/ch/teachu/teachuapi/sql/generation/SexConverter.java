package ch.teachu.teachuapi.sql.generation;

import ch.teachu.teachuapi.enums.Sex;
import org.jooq.Converter;

public class SexConverter implements Converter<String, Sex> {

    @Override
    public Sex from(String databaseObject) {
        return Sex.valueOf(databaseObject);
    }

    @Override
    public String to(Sex userObject) {
        return userObject.name();
    }

    @Override
    public Class<String> fromType() {
        return String.class;
    }

    @Override
    public Class<Sex> toType() {
        return Sex.class;
    }
}
