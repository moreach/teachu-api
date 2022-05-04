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
                                    .withForcedTypes(new ForcedType()
                                            .withUserType("java.util.UUID")
                                            .withIncludeExpression(".*id")
                                            .withConverter("ch.teachu.teachuapi.sql.generation.UuidConverter")))
                            .withTarget(new Target()
                                    .withPackageName("ch.teachu.techuapi.generated")
                                    .withDirectory("src/generated/java"))));
        } catch (IOException io) {
            throw new RuntimeException(io);
        }
    }
}
