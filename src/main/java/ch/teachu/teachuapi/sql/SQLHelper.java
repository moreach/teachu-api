package ch.teachu.teachuapi.sql;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLHelper {

    public static void mapPreparedStatement(PreparedStatement preparedStatement, Object input, List<String> binds) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, SQLException {
        for (int i = 0; i < binds.size(); i++) {
            preparedStatement.setObject((i + 1), invokeGetter(input, binds.get(i)));
        }
    }

    public static void mapResultSet(ResultSet resultSet, Object output, List<String> fields) throws NoSuchFieldException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        for (int i = 0; i < fields.size(); i++) {
            invokeSetter(
                    output,
                    fields.get(i),
                    resultSet.getObject(
                            (i + 1),
                            output.getClass().getDeclaredField(fields.get(i)).getType()
                    ));
        }
    }

    public static List<String> findBinds(String query) {
        List<String> binds = new ArrayList<>();

        String[] splitQuery = query.split("[ \\t\\n(),]");
        for (String queryPart : splitQuery) {
            if (queryPart.startsWith(":")) {

                binds.add(queryPart.replace(",", "")
                        .replace(":", ""));
            }
        }

        return binds;
    }

    public static List<String> findInputs(String query) {
        List<String> binds = new ArrayList<>();

        String[] splitQuery = query.split("[ \\t\\n(),]");
        for (String queryPart : splitQuery) {
            if (queryPart.startsWith("-")) {

                binds.add(queryPart.replace(",", "")
                        .replace("-", ""));
            }
        }

        return binds;
    }

    public static String replaceInputs(String query, List<String> inputs) {
        for (String input : inputs) {
            query = query.replaceFirst("-" + input, "?");
        }

        return query;
    }

    public static String removeInto(String query) {
        int intoIndex = query.toLowerCase().indexOf("into");

        return query.substring(0, intoIndex);
    }

    private static Object invokeGetter(Object dto, String field) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return dto.getClass()
                .getDeclaredMethod(functionName("get", field))
                .invoke(dto);
    }

    private static void invokeSetter(Object dto, String field, Object arg) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        dto.getClass()
                .getDeclaredMethod(
                        functionName("set", field),
                        dto.getClass()
                                .getDeclaredField(field)
                                .getType())
                .invoke(dto, arg);
    }

    private static String functionName(String prefix, String field) {
        return prefix + field.substring(0, 1).toUpperCase() + field.substring(1);
    }
}
