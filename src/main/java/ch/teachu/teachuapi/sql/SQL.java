package ch.teachu.teachuapi.sql;

import ch.teachu.teachuapi.parent.properties.DatabaseProperties;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Constructor;
import java.sql.*;
import java.util.List;

@Repository
public class SQL {

    private static Connection connection;

    public SQL(DatabaseProperties databaseProperties) throws SQLException {
        connection = DriverManager.getConnection(databaseProperties.getUrl(), databaseProperties.getUsername(), databaseProperties.getPassword());
    }

    public static void select(String query, Object output) {
        try {
            List<String> binds = SQLHelper.findBinds(query);
            query = SQLHelper.removeInto(query);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return;
            }

            SQLHelper.mapResultSet(resultSet, output, binds);

        } catch (Exception e) {
            throw new RuntimeException("Failed to executed SQL: " + e.getMessage());
        }
    }

    public static void select(String query, Object output, Object input) {
        try {
            List<String> binds = SQLHelper.findBinds(query);
            List<String> inputs = SQLHelper.findInputs(query);
            query = SQLHelper.replaceInputs(query, inputs);
            query = SQLHelper.removeInto(query);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            SQLHelper.mapPreparedStatement(preparedStatement, input, inputs);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return;
            }

            SQLHelper.mapResultSet(resultSet, output, binds);

        } catch (Exception e) {
            throw new RuntimeException("Failed to executed SQL: " + e.getMessage());
        }
    }

    public static void select(String query, List output, Class<?> clazz) {
        try {
            List<String> binds = SQLHelper.findBinds(query);
            query = SQLHelper.removeInto(query);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return;
            }

            do {
                Constructor<?> constructor = clazz.getConstructor();
                Object object = constructor.newInstance();

                SQLHelper.mapResultSet(resultSet, object, binds);

                output.add(object);
            } while (resultSet.next());
        } catch (Exception e) {
            throw new RuntimeException("Failed to executed SQL: " + e.getMessage());
        }
    }

    public static void select(String query, List output, Class<?> clazz, Object input) {
        try {
            List<String> binds = SQLHelper.findBinds(query);
            List<String> inputs = SQLHelper.findInputs(query);
            query = SQLHelper.replaceInputs(query, inputs);
            query = SQLHelper.removeInto(query);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            SQLHelper.mapPreparedStatement(preparedStatement, input, inputs);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return;
            }

            do {
                Constructor<?> constructor = clazz.getConstructor();
                Object object = constructor.newInstance();

                SQLHelper.mapResultSet(resultSet, object, binds);

                output.add(object);
            } while (resultSet.next());
        } catch (Exception e) {
            throw new RuntimeException("Failed to executed SQL: " + e.getMessage());
        }
    }

    public static int insert(String query, Object input) {
        try {
            List<String> inputs = SQLHelper.findInputs(query);
            query = SQLHelper.replaceInputs(query, inputs);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            SQLHelper.mapPreparedStatement(preparedStatement, input, inputs);

            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Failed to executed SQL: " + e.getMessage());
        }
    }

    public static int update(String query, Object input) {
        try {
            List<String> inputs = SQLHelper.findInputs(query);
            query = SQLHelper.replaceInputs(query, inputs);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            SQLHelper.mapPreparedStatement(preparedStatement, input, inputs);

            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Failed to executed SQL: " + e.getMessage());
        }
    }

    public static int delete(String query, Object input) {
        try {
            List<String> inputs = SQLHelper.findInputs(query);
            query = SQLHelper.replaceInputs(query, inputs);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            SQLHelper.mapPreparedStatement(preparedStatement, input, inputs);

            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Failed to executed SQL: " + e.getMessage());
        }
    }
}
