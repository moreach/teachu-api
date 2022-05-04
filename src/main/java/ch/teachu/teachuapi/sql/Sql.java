package ch.teachu.teachuapi.sql;

import ch.teachu.teachuapi.properties.DatabaseProperties;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.stereotype.Service;

import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class Sql {
    public static DSLContext SQL;

    private static DatabaseProperties databaseProperties;

    public Sql(DatabaseProperties databaseProperties) {
        Sql.databaseProperties = databaseProperties;
        SQL = new DefaultDSLContext(configuration());
    }

    public static DefaultConfiguration configuration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        try {
            jooqConfiguration.set(DriverManager.getConnection(databaseProperties.getUrl(), databaseProperties.getUsername(), databaseProperties.getPassword()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        jooqConfiguration.set(SQLDialect.MYSQL);
        return jooqConfiguration;
    }
}
