//package ch.teachu.old.util;
//
//import ch.teachu.old.sql.Sql;
//import ch.teachu.teachuapi.enums.LogLevel;
//import ch.teachu.teachuapi.generated.tables.Log;
//import ch.teachu.teachuapi.generated.tables.records.LogRecord;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//public class LogUtil {
//
//    public static void log(String message, Class<?> origin, LogLevel level) {
//        LogRecord logRecord = Sql.SQL.newRecord(Log.LOG);
//        logRecord.setId(UUID.randomUUID());
//        logRecord.setMessage(message);
//        logRecord.setOrigin(origin.getSimpleName());
//        logRecord.setLevel(level);
//        logRecord.setTimestamp(LocalDateTime.now());
//        logRecord.store();
//    }
//
//    public static void log(String message, String origin, LogLevel level) {
//        LogRecord logRecord = Sql.SQL.newRecord(Log.LOG);
//        logRecord.setId(UUID.randomUUID());
//        logRecord.setMessage(message);
//        logRecord.setOrigin(origin);
//        logRecord.setLevel(level);
//        logRecord.setTimestamp(LocalDateTime.now());
//        logRecord.store();
//    }
//}
