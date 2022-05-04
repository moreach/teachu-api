/*
 * This file is generated by jOOQ.
 */
package ch.teachu.techuapi.generated.tables;


import ch.teachu.teachuapi.enums.ClassSubjectInterval;
import ch.teachu.teachuapi.sql.generation.ClassSubjectIntervalConverter;
import ch.teachu.teachuapi.sql.generation.UuidConverter;
import ch.teachu.techuapi.generated.Keys;
import ch.teachu.techuapi.generated.Teachu;
import ch.teachu.techuapi.generated.tables.records.ClassSubjectRecord;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row9;
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
public class ClassSubject extends TableImpl<ClassSubjectRecord> {

    private static final long serialVersionUID = -1361858191;

    /**
     * The reference instance of <code>teachu.class_subject</code>
     */
    public static final ClassSubject CLASS_SUBJECT = new ClassSubject();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ClassSubjectRecord> getRecordType() {
        return ClassSubjectRecord.class;
    }

    /**
     * The column <code>teachu.class_subject.id</code>.
     */
    public final TableField<ClassSubjectRecord, UUID> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BINARY(16).nullable(false), this, "", new UuidConverter());

    /**
     * The column <code>teachu.class_subject.class_id</code>.
     */
    public final TableField<ClassSubjectRecord, UUID> CLASS_ID = createField(DSL.name("class_id"), org.jooq.impl.SQLDataType.BINARY(16), this, "", new UuidConverter());

    /**
     * The column <code>teachu.class_subject.teacher_id</code>.
     */
    public final TableField<ClassSubjectRecord, UUID> TEACHER_ID = createField(DSL.name("teacher_id"), org.jooq.impl.SQLDataType.BINARY(16), this, "", new UuidConverter());

    /**
     * The column <code>teachu.class_subject.subject_id</code>.
     */
    public final TableField<ClassSubjectRecord, UUID> SUBJECT_ID = createField(DSL.name("subject_id"), org.jooq.impl.SQLDataType.BINARY(16), this, "", new UuidConverter());

    /**
     * The column <code>teachu.class_subject.note</code>.
     */
    public final TableField<ClassSubjectRecord, String> NOTE = createField(DSL.name("note"), org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>teachu.class_subject.start_date</code>.
     */
    public final TableField<ClassSubjectRecord, LocalDate> START_DATE = createField(DSL.name("start_date"), org.jooq.impl.SQLDataType.LOCALDATE, this, "");

    /**
     * The column <code>teachu.class_subject.end_date</code>.
     */
    public final TableField<ClassSubjectRecord, LocalDate> END_DATE = createField(DSL.name("end_date"), org.jooq.impl.SQLDataType.LOCALDATE, this, "");

    /**
     * The column <code>teachu.class_subject.interval</code>.
     */
    public final TableField<ClassSubjectRecord, ClassSubjectInterval> INTERVAL = createField(DSL.name("interval"), org.jooq.impl.SQLDataType.VARCHAR(100), this, "", new ClassSubjectIntervalConverter());

    /**
     * The column <code>teachu.class_subject.active</code>.
     */
    public final TableField<ClassSubjectRecord, Byte> ACTIVE = createField(DSL.name("active"), org.jooq.impl.SQLDataType.TINYINT, this, "");

    /**
     * Create a <code>teachu.class_subject</code> table reference
     */
    public ClassSubject() {
        this(DSL.name("class_subject"), null);
    }

    /**
     * Create an aliased <code>teachu.class_subject</code> table reference
     */
    public ClassSubject(String alias) {
        this(DSL.name(alias), CLASS_SUBJECT);
    }

    /**
     * Create an aliased <code>teachu.class_subject</code> table reference
     */
    public ClassSubject(Name alias) {
        this(alias, CLASS_SUBJECT);
    }

    private ClassSubject(Name alias, Table<ClassSubjectRecord> aliased) {
        this(alias, aliased, null);
    }

    private ClassSubject(Name alias, Table<ClassSubjectRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> ClassSubject(Table<O> child, ForeignKey<O, ClassSubjectRecord> key) {
        super(child, key, CLASS_SUBJECT);
    }

    @Override
    public Schema getSchema() {
        return Teachu.TEACHU;
    }

    @Override
    public UniqueKey<ClassSubjectRecord> getPrimaryKey() {
        return Keys.KEY_CLASS_SUBJECT_PRIMARY;
    }

    @Override
    public List<UniqueKey<ClassSubjectRecord>> getKeys() {
        return Arrays.<UniqueKey<ClassSubjectRecord>>asList(Keys.KEY_CLASS_SUBJECT_PRIMARY);
    }

    @Override
    public ClassSubject as(String alias) {
        return new ClassSubject(DSL.name(alias), this);
    }

    @Override
    public ClassSubject as(Name alias) {
        return new ClassSubject(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ClassSubject rename(String name) {
        return new ClassSubject(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ClassSubject rename(Name name) {
        return new ClassSubject(name, null);
    }

    // -------------------------------------------------------------------------
    // Row9 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row9<UUID, UUID, UUID, UUID, String, LocalDate, LocalDate, ClassSubjectInterval, Byte> fieldsRow() {
        return (Row9) super.fieldsRow();
    }
}
