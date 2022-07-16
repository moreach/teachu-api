//package ch.teachu.old.parent;
//
//import ch.teachu.old.sql.Sql;
//import org.jooq.Condition;
//import org.jooq.DSLContext;
//import org.jooq.Field;
//import org.jooq.Record;
//import org.jooq.impl.TableImpl;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.UUID;
//
//@Repository
//public class AbstractRepo {
//
//    private DSLContext SQL;
//
//    protected DSLContext sql() {
//        if (SQL == null) {
//            SQL = Sql.SQL;
//        }
//        return SQL;
//    }
//
//    public <T extends Record> List<T> findAll(TableImpl<T> table) {
//        return sql().selectFrom(table).fetchInto(table);
//    }
//
//    public <T extends Record> T findById(TableImpl<T> table, UUID id) {
//        return sql().selectFrom(table).where(getIdField(table).eq(id)).fetchOneInto(table);
//    }
//
//    public boolean existsById(TableImpl<?> table, UUID id) {
//        return sql().fetchExists(sql().selectFrom(table).where(getIdField(table).eq(id)));
//    }
//
//    public int deleteById(TableImpl<?> table, UUID id) {
//        return sql().delete(table).where(getIdField(table).eq(id)).execute();
//    }
//
//    @SuppressWarnings("unchecked")
//    private Field<UUID> getIdField(TableImpl<?> table) {
//        try {
//            return (Field<UUID>) table.getClass().getField("ID").get(table);
//        } catch (IllegalAccessException | NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * Adds a condition to a list if the given binding is not null.
//     * @param conditionList The list to add the condition to.
//     * @param conditionToAdd The condition which should be added to the list.
//     * @param binding The value used in the condition which will determine whether the condition should be added or not.
//     */
//    protected void addCondition(List<Condition> conditionList, Condition conditionToAdd, Object binding) {
//        if (binding != null) {
//            conditionList.add(conditionToAdd);
//        }
//    }
//}
