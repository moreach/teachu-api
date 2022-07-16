//package ch.teachu.old.sql.generation;
//
//import ch.teachu.old.enums.Weekday;
//import org.jooq.Converter;
//
//public class WeekdayConverter implements Converter<String, Weekday> {
//
//    @Override
//    public Weekday from(String databaseObject) {
//        return Weekday.valueOf(databaseObject.toUpperCase());
//    }
//
//    @Override
//    public String to(Weekday userObject) {
//        return userObject.name().toLowerCase();
//    }
//
//    @Override
//    public Class<String> fromType() {
//        return String.class;
//    }
//
//    @Override
//    public Class<Weekday> toType() {
//        return Weekday.class;
//    }
//}
