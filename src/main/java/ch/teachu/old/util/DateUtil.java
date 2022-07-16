//package ch.teachu.old.util;
//
//import lombok.experimental.UtilityClass;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.Date;
//
//@UtilityClass
//public class DateUtil {
//
//    public Date toDate(LocalDateTime localDateTime) {
//        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
//    }
//
//    public LocalDateTime toLocalDateTime(Date date) {
//        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
//    }
//
//    public LocalDate toLocalDate(Date date) {
//        return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
//    }
//
//    public static Date toDate(LocalDate localDate) {
//        return toDate(LocalDateTime.from(localDate.atStartOfDay()));
//    }
//}
