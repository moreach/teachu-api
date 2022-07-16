//package ch.teachu.old.sql.generation;
//
//import ch.teachu.teachuapi.enums.LogLevel;
//import org.jooq.Converter;
//
//public class LogLevelConverter implements Converter<String, LogLevel> {
//
//    @Override
//    public LogLevel from(String databaseObject) {
//        return LogLevel.valueOf(databaseObject.toUpperCase());
//    }
//
//    @Override
//    public String to(LogLevel userObject) {
//        return userObject.name().toLowerCase();
//    }
//
//    @Override
//    public Class<String> fromType() {
//        return String.class;
//    }
//
//    @Override
//    public Class<LogLevel> toType() {
//        return LogLevel.class;
//    }
//}
