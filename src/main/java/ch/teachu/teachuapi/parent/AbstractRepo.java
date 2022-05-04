package ch.teachu.teachuapi.parent;

import ch.teachu.teachuapi.sql.Sql;
import ch.teachu.techuapi.generated.tables.SchoolClass;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.TableImpl;
import org.jooq.impl.UpdatableRecordImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class AbstractRepo {

    private DSLContext SQL;

    protected DSLContext sql() {
        if (SQL == null) {
            SQL = Sql.SQL;
        }
        return SQL;
    }

    public <T extends Record> List<T> findAll(TableImpl<T> table) {
        return sql().selectFrom(table).fetchInto(table);
    }

    public <T extends Record> T findById(TableImpl<T> table, UUID id) {
        return sql().selectFrom(table).where(getIdField(table).eq(id)).fetchOneInto(table);
    }

    public void deleteById(TableImpl<?> table, UUID id) {
        sql().delete(table).where(getIdField(table).eq(id)).execute();
    }

    @SuppressWarnings("unchecked")
    protected Field<UUID> getIdField(TableImpl<?> table) {
        try {
            return (Field<UUID>) table.getClass().getField("ID").get(table);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
