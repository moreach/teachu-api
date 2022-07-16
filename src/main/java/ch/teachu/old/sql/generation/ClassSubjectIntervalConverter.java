//package ch.teachu.old.sql.generation;
//
//import ch.teachu.teachuapi.enums.ClassSubjectInterval;
//import org.jooq.Converter;
//
//public class ClassSubjectIntervalConverter implements Converter<String, ClassSubjectInterval> {
//
//    @Override
//    public ClassSubjectInterval from(String databaseObject) {
//        return ClassSubjectInterval.valueOf(databaseObject.toUpperCase());
//    }
//
//    @Override
//    public String to(ClassSubjectInterval userObject) {
//        return userObject.name().toLowerCase();
//    }
//
//    @Override
//    public Class<String> fromType() {
//        return String.class;
//    }
//
//    @Override
//    public Class<ClassSubjectInterval> toType() {
//        return ClassSubjectInterval.class;
//    }
//}
