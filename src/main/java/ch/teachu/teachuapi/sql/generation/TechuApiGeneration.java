package ch.teachu.teachuapi.sql.generation;

import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;

import java.io.*;
import java.util.Properties;

public class TechuApiGeneration {

    public static void main(String[] args) throws Exception {
        try (InputStream input = TechuApiGeneration.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            String url = prop.getProperty("database.url");
            String user = prop.getProperty("database.username");
            String password = prop.getProperty("database.password");

            GenerationTool.generate(new Configuration()
                    .withJdbc(new Jdbc()
                            .withUser(user)
                            .withUrl(url)
                            .withPassword(password))
                    .withGenerator(new Generator()
                            .withName("org.jooq.codegen.JavaGenerator")
                            .withDatabase(new Database()
                                    .withName("org.jooq.meta.mysql.MySQLDatabase")
                                    .withInputSchema("teachu")
                                    .withForcedTypes(
                                            new ForcedType()
                                                .withUserType("java.util.UUID")
                                                .withIncludeExpression(".*id")
                                                .withConverter("ch.teachu.teachuapi.sql.generation.UuidConverter"),
                                            new ForcedType()
                                                    .withUserType("ch.teachu.teachuapi.enums.Weekday")
                                                    .withIncludeTypes("weekday")
                                                    .withConverter("ch.teachu.teachuapi.sql.generation.WeekdayConverter"),
                                            new ForcedType()
                                                    .withUserType("ch.teachu.teachuapi.enums.Role")
                                                    .withIncludeExpression("role")
                                                    .withConverter("ch.teachu.teachuapi.sql.generation.RoleConverter"),
                                            new ForcedType()
                                                    .withUserType("ch.teachu.teachuapi.enums.ChatState")
                                                    .withIncludeExpression("chat_state")
                                                    .withConverter("ch.teachu.teachuapi.sql.generation.ChatStateConverter"),
                                            new ForcedType()
                                                    .withUserType("ch.teachu.teachuapi.enums.ClassSubjectInterval")
                                                    .withIncludeExpression("interval")
                                                    .withConverter("ch.teachu.teachuapi.sql.generation.ClassSubjectIntervalConverter"),
                                            new ForcedType()
                                                    .withUserType("ch.teachu.teachuapi.enums.Language")
                                                    .withIncludeExpression("language")
                                                    .withConverter("ch.teachu.teachuapi.sql.generation.LanguageConverter"),
                                            new ForcedType()
                                                    .withUserType("ch.teachu.teachuapi.enums.LogLevel")
                                                    .withIncludeExpression("level")
                                                    .withConverter("ch.teachu.teachuapi.sql.generation.LogLevelConverter"),
                                            new ForcedType()
                                                    .withUserType("ch.teachu.teachuapi.enums.SchoolInfoState")
                                                    .withIncludeExpression("school_info_state")
                                                    .withConverter("ch.teachu.teachuapi.sql.generation.SchoolInfoStateConverter"),
                                            new ForcedType()
                                                    .withUserType("ch.teachu.teachuapi.enums.Sex")
                                                    .withIncludeExpression("sex")
                                                    .withConverter("ch.teachu.teachuapi.sql.generation.SexConverter"),
                                            new ForcedType()
                                                    .withUserType("ch.teachu.teachuapi.enums.UserEventState")
                                                    .withIncludeExpression("user_event_state")
                                                    .withConverter("ch.teachu.teachuapi.sql.generation.UserEventStateConverter")))
                            .withTarget(new Target()
                                    .withPackageName("ch.teachu.techuapi.generated")
                                    .withDirectory("src/generated/java"))));
        } catch (IOException io) {
            throw new RuntimeException(io);
        }
    }
}
