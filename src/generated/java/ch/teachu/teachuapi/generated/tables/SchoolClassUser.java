/*
 * This file is generated by jOOQ.
 */
package ch.teachu.teachuapi.generated.tables;


import ch.teachu.teachuapi.generated.Teachu;
import ch.teachu.teachuapi.generated.tables.records.SchoolClassUserRecord;
import ch.teachu.teachuapi.sql.generation.UuidConverter;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.util.UUID;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SchoolClassUser extends TableImpl<SchoolClassUserRecord> {

    private static final long serialVersionUID = 915101936;

    /**
     * The reference instance of <code>teachu.school_class_user</code>
     */
    public static final SchoolClassUser SCHOOL_CLASS_USER = new SchoolClassUser();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SchoolClassUserRecord> getRecordType() {
        return SchoolClassUserRecord.class;
    }

    /**
     * The column <code>teachu.school_class_user.school_class_id</code>.
     */
    public final TableField<SchoolClassUserRecord, UUID> SCHOOL_CLASS_ID = createField(DSL.name("school_class_id"), org.jooq.impl.SQLDataType.BINARY(16), this, "", new UuidConverter());

    /**
     * The column <code>teachu.school_class_user.user_id</code>.
     */
    public final TableField<SchoolClassUserRecord, UUID> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BINARY(16), this, "", new UuidConverter());

    /**
     * Create a <code>teachu.school_class_user</code> table reference
     */
    public SchoolClassUser() {
        this(DSL.name("school_class_user"), null);
    }

    /**
     * Create an aliased <code>teachu.school_class_user</code> table reference
     */
    public SchoolClassUser(String alias) {
        this(DSL.name(alias), SCHOOL_CLASS_USER);
    }

    /**
     * Create an aliased <code>teachu.school_class_user</code> table reference
     */
    public SchoolClassUser(Name alias) {
        this(alias, SCHOOL_CLASS_USER);
    }

    private SchoolClassUser(Name alias, Table<SchoolClassUserRecord> aliased) {
        this(alias, aliased, null);
    }

    private SchoolClassUser(Name alias, Table<SchoolClassUserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> SchoolClassUser(Table<O> child, ForeignKey<O, SchoolClassUserRecord> key) {
        super(child, key, SCHOOL_CLASS_USER);
    }

    @Override
    public Schema getSchema() {
        return Teachu.TEACHU;
    }

    @Override
    public SchoolClassUser as(String alias) {
        return new SchoolClassUser(DSL.name(alias), this);
    }

    @Override
    public SchoolClassUser as(Name alias) {
        return new SchoolClassUser(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public SchoolClassUser rename(String name) {
        return new SchoolClassUser(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public SchoolClassUser rename(Name name) {
        return new SchoolClassUser(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<UUID, UUID> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}