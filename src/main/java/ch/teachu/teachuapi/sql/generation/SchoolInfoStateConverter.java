package ch.teachu.teachuapi.sql.generation;

import ch.teachu.teachuapi.enums.SchoolInfoState;
import org.jooq.Converter;

public class SchoolInfoStateConverter implements Converter<String, SchoolInfoState> {

    @Override
    public SchoolInfoState from(String databaseObject) {
        return SchoolInfoState.valueOf(databaseObject);
    }

    @Override
    public String to(SchoolInfoState userObject) {
        return userObject.name();
    }

    @Override
    public Class<String> fromType() {
        return String.class;
    }

    @Override
    public Class<SchoolInfoState> toType() {
        return SchoolInfoState.class;
    }
}
