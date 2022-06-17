/*
 * This file is generated by jOOQ.
 */
package ch.teachu.teachuapi.generated.tables;


import ch.teachu.teachuapi.generated.Keys;
import ch.teachu.teachuapi.generated.Teachu;
import ch.teachu.teachuapi.generated.tables.records.SchoolClassRecord;
import ch.teachu.teachuapi.sql.generation.UuidConverter;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SchoolClass extends TableImpl<SchoolClassRecord> {

    private static final long serialVersionUID = 1564658586;

    /**
     * The reference instance of <code>teachu.school_class</code>
     */
    public static final SchoolClass SCHOOL_CLASS = new SchoolClass();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SchoolClassRecord> getRecordType() {
        return SchoolClassRecord.class;
    }

    /**
     * The column <code>teachu.school_class.id</code>.
     */
    public final TableField<SchoolClassRecord, UUID> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BINARY(16).nullable(false), this, "", new UuidConverter());

    /**
     * The column <code>teachu.school_class.name</code>.
     */
    public final TableField<SchoolClassRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>teachu.school_class.teacher_id</code>.
     */
    public final TableField<SchoolClassRecord, UUID> TEACHER_ID = createField(DSL.name("teacher_id"), org.jooq.impl.SQLDataType.BINARY(16), this, "", new UuidConverter());

    /**
     * Create a <code>teachu.school_class</code> table reference
     */
    public SchoolClass() {
        this(DSL.name("school_class"), null);
    }

    /**
     * Create an aliased <code>teachu.school_class</code> table reference
     */
    public SchoolClass(String alias) {
        this(DSL.name(alias), SCHOOL_CLASS);
    }

    /**
     * Create an aliased <code>teachu.school_class</code> table reference
     */
    public SchoolClass(Name alias) {
        this(alias, SCHOOL_CLASS);
    }

    private SchoolClass(Name alias, Table<SchoolClassRecord> aliased) {
        this(alias, aliased, null);
    }

    private SchoolClass(Name alias, Table<SchoolClassRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> SchoolClass(Table<O> child, ForeignKey<O, SchoolClassRecord> key) {
        super(child, key, SCHOOL_CLASS);
    }

    @Override
    public Schema getSchema() {
        return Teachu.TEACHU;
    }

    @Override
    public UniqueKey<SchoolClassRecord> getPrimaryKey() {
        return Keys.KEY_SCHOOL_CLASS_PRIMARY;
    }

    @Override
    public List<UniqueKey<SchoolClassRecord>> getKeys() {
        return Arrays.<UniqueKey<SchoolClassRecord>>asList(Keys.KEY_SCHOOL_CLASS_PRIMARY);
    }

    @Override
    public SchoolClass as(String alias) {
        return new SchoolClass(DSL.name(alias), this);
    }

    @Override
    public SchoolClass as(Name alias) {
        return new SchoolClass(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public SchoolClass rename(String name) {
        return new SchoolClass(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public SchoolClass rename(Name name) {
        return new SchoolClass(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<UUID, String, UUID> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
