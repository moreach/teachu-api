/*
 * This file is generated by jOOQ.
 */
package ch.teachu.teachuapi.generated.tables;


import ch.teachu.teachuapi.generated.Keys;
import ch.teachu.teachuapi.generated.Teachu;
import ch.teachu.teachuapi.generated.tables.records.SemesterRecord;
import ch.teachu.teachuapi.sql.generation.UuidConverter;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Semester extends TableImpl<SemesterRecord> {

    private static final long serialVersionUID = 1035368494;

    /**
     * The reference instance of <code>teachu.semester</code>
     */
    public static final Semester SEMESTER = new Semester();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SemesterRecord> getRecordType() {
        return SemesterRecord.class;
    }

    /**
     * The column <code>teachu.semester.id</code>.
     */
    public final TableField<SemesterRecord, UUID> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BINARY(16).nullable(false), this, "", new UuidConverter());

    /**
     * The column <code>teachu.semester.name</code>.
     */
    public final TableField<SemesterRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(250), this, "");

    /**
     * The column <code>teachu.semester.from</code>.
     */
    public final TableField<SemesterRecord, LocalDateTime> FROM = createField(DSL.name("from"), org.jooq.impl.SQLDataType.LOCALDATETIME, this, "");

    /**
     * The column <code>teachu.semester.to</code>.
     */
    public final TableField<SemesterRecord, LocalDateTime> TO = createField(DSL.name("to"), org.jooq.impl.SQLDataType.LOCALDATETIME, this, "");

    /**
     * Create a <code>teachu.semester</code> table reference
     */
    public Semester() {
        this(DSL.name("semester"), null);
    }

    /**
     * Create an aliased <code>teachu.semester</code> table reference
     */
    public Semester(String alias) {
        this(DSL.name(alias), SEMESTER);
    }

    /**
     * Create an aliased <code>teachu.semester</code> table reference
     */
    public Semester(Name alias) {
        this(alias, SEMESTER);
    }

    private Semester(Name alias, Table<SemesterRecord> aliased) {
        this(alias, aliased, null);
    }

    private Semester(Name alias, Table<SemesterRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Semester(Table<O> child, ForeignKey<O, SemesterRecord> key) {
        super(child, key, SEMESTER);
    }

    @Override
    public Schema getSchema() {
        return Teachu.TEACHU;
    }

    @Override
    public UniqueKey<SemesterRecord> getPrimaryKey() {
        return Keys.KEY_SEMESTER_PRIMARY;
    }

    @Override
    public List<UniqueKey<SemesterRecord>> getKeys() {
        return Arrays.<UniqueKey<SemesterRecord>>asList(Keys.KEY_SEMESTER_PRIMARY);
    }

    @Override
    public Semester as(String alias) {
        return new Semester(DSL.name(alias), this);
    }

    @Override
    public Semester as(Name alias) {
        return new Semester(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Semester rename(String name) {
        return new Semester(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Semester rename(Name name) {
        return new Semester(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<UUID, String, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
