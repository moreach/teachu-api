//package ch.teachu.old.sql.generation;
//
//import ch.teachu.old.enums.UserLanguage;
//import org.jooq.Converter;
//
//public class UserLanguageConverter implements Converter<String, UserLanguage> {
//
//    @Override
//    public UserLanguage from(String databaseObject) {
//        return UserLanguage.valueOf(databaseObject.toUpperCase());
//    }
//
//    @Override
//    public String to(UserLanguage userObject) {
//        return userObject.name().toLowerCase();
//    }
//
//    @Override
//    public Class<String> fromType() {
//        return String.class;
//    }
//
//    @Override
//    public Class<UserLanguage> toType() {
//        return UserLanguage.class;
//    }
//}
