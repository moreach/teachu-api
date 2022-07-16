//package ch.teachu.old.sql.generation;
//
//import org.jooq.codegen.GenerationTool;
//import org.jooq.meta.jaxb.*;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
//public class TeachuApiGeneration {
//
//    public static void main(String[] args) throws Exception {
//        try (InputStream input = TeachuApiGeneration.class.getClassLoader().getResourceAsStream("application-dev.properties")) {
//            Properties prop = new Properties();
//            prop.load(input);
//
//            String url = prop.getProperty("database.url");
//            String user = prop.getProperty("database.username");
//            String password = prop.getProperty("database.password");
//
//            GenerationTool.generate(new Configuration()
//                    .withJdbc(new Jdbc()
//                            .withUser(user)
//                            .withUrl(url)
//                            .withPassword(password))
//                    .withGenerator(new Generator()
//                            .withName("org.jooq.codegen.JavaGenerator")
//                            .withDatabase(new Database()
//                                    .withName("org.jooq.meta.mysql.MySQLDatabase")
//                                    .withInputSchema("teachu")
//                                    .withForcedTypes(
//                                            new ForcedType()
//                                                .withUserType("java.util.UUID")
//                                                .withIncludeExpression(".*id")
//                                                .withConverter("ch.teachu.teachuapi.sql.generation.UuidConverter"),
//                                            new ForcedType()
//                                                    .withName("BOOLEAN")
//                                                    .withIncludeTypes("(?i:TINYINT\\(1\\))"),
//                                            new ForcedType()
//                                                    .withUserType("ch.teachu.teachuapi.enums.Weekday")
//                                                    .withIncludeTypes("weekday")
//                                                    .withConverter("ch.teachu.teachuapi.sql.generation.WeekdayConverter"),
//                                            new ForcedType()
//                                                    .withUserType("ch.teachu.teachuapi.enums.UserRole")
//                                                    .withIncludeExpression("role")
//                                                    .withConverter("ch.teachu.teachuapi.sql.generation.UserRoleConverter"),
//                                            new ForcedType()
//                                                    .withUserType("ch.teachu.teachuapi.enums.ChatState")
//                                                    .withIncludeExpression("chat_state")
//                                                    .withConverter("ch.teachu.teachuapi.sql.generation.ChatStateConverter"),
//                                            new ForcedType()
//                                                    .withUserType("ch.teachu.teachuapi.enums.ClassSubjectInterval")
//                                                    .withIncludeExpression("interval")
//                                                    .withConverter("ch.teachu.teachuapi.sql.generation.ClassSubjectIntervalConverter"),
//                                            new ForcedType()
//                                                    .withUserType("ch.teachu.teachuapi.enums.UserLanguage")
//                                                    .withIncludeExpression("language")
//                                                    .withConverter("ch.teachu.teachuapi.sql.generation.UserLanguageConverter"),
//                                            new ForcedType()
//                                                    .withUserType("ch.teachu.teachuapi.enums.LogLevel")
//                                                    .withIncludeExpression("level")
//                                                    .withConverter("ch.teachu.teachuapi.sql.generation.LogLevelConverter"),
//                                            new ForcedType()
//                                                    .withUserType("ch.teachu.teachuapi.enums.SchoolInfoState")
//                                                    .withIncludeExpression("school_info_state")
//                                                    .withConverter("ch.teachu.teachuapi.sql.generation.SchoolInfoStateConverter"),
//                                            new ForcedType()
//                                                    .withUserType("ch.teachu.teachuapi.enums.UserSex")
//                                                    .withIncludeExpression("sex")
//                                                    .withConverter("ch.teachu.teachuapi.sql.generation.UserSexConverter"),
//                                            new ForcedType()
//                                                    .withUserType("ch.teachu.teachuapi.enums.UserEventState")
//                                                    .withIncludeExpression("user_event_state")
//                                                    .withConverter("ch.teachu.teachuapi.sql.generation.UserEventStateConverter")))
//                            .withTarget(new Target()
//                                    .withPackageName("ch.teachu.teachuapi.generated")
//                                    .withDirectory("src/generated/java"))));
//        } catch (IOException io) {
//            throw new RuntimeException(io);
//        }
//    }
//}
