/*
 * This file is generated by jOOQ.
 */
package ch.teachu.teachuapi.generated.tables;


import ch.teachu.teachuapi.generated.Keys;
import ch.teachu.teachuapi.generated.Teachu;
import ch.teachu.teachuapi.generated.tables.records.ExamRecord;
import ch.teachu.teachuapi.sql.generation.UuidConverter;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Exam extends TableImpl<ExamRecord> {

    private static final long serialVersionUID = 1091743481;

    /**
     * The reference instance of <code>teachu.exam</code>
     */
    public static final Exam EXAM = new Exam();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ExamRecord> getRecordType() {
        return ExamRecord.class;
    }

    /**
     * The column <code>teachu.exam.id</code>.
     */
    public final TableField<ExamRecord, UUID> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BINARY(16).nullable(false), this, "", new UuidConverter());

    /**
     * The column <code>teachu.exam.school_class_subject_id</code>.
     */
    public final TableField<ExamRecord, UUID> SCHOOL_CLASS_SUBJECT_ID = createField(DSL.name("school_class_subject_id"), org.jooq.impl.SQLDataType.BINARY(16), this, "", new UuidConverter());

    /**
     * The column <code>teachu.exam.name</code>.
     */
    public final TableField<ExamRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>teachu.exam.description</code>.
     */
    public final TableField<ExamRecord, String> DESCRIPTION = createField(DSL.name("description"), org.jooq.impl.SQLDataType.VARCHAR(250), this, "");

    /**
     * The column <code>teachu.exam.weight</code>.
     */
    public final TableField<ExamRecord, Double> WEIGHT = createField(DSL.name("weight"), org.jooq.impl.SQLDataType.FLOAT, this, "");

    /**
     * The column <code>teachu.exam.date</code>.
     */
    public final TableField<ExamRecord, LocalDate> DATE = createField(DSL.name("date"), org.jooq.impl.SQLDataType.LOCALDATE, this, "");

    /**
     * The column <code>teachu.exam.view_date</code>.
     */
    public final TableField<ExamRecord, LocalDate> VIEW_DATE = createField(DSL.name("view_date"), org.jooq.impl.SQLDataType.LOCALDATE, this, "");

    /**
     * Create a <code>teachu.exam</code> table reference
     */
    public Exam() {
        this(DSL.name("exam"), null);
    }

    /**
     * Create an aliased <code>teachu.exam</code> table reference
     */
    public Exam(String alias) {
        this(DSL.name(alias), EXAM);
    }

    /**
     * Create an aliased <code>teachu.exam</code> table reference
     */
    public Exam(Name alias) {
        this(alias, EXAM);
    }

    private Exam(Name alias, Table<ExamRecord> aliased) {
        this(alias, aliased, null);
    }

    private Exam(Name alias, Table<ExamRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Exam(Table<O> child, ForeignKey<O, ExamRecord> key) {
        super(child, key, EXAM);
    }

    @Override
    public Schema getSchema() {
        return Teachu.TEACHU;
    }

    @Override
    public UniqueKey<ExamRecord> getPrimaryKey() {
        return Keys.KEY_EXAM_PRIMARY;
    }

    @Override
    public List<UniqueKey<ExamRecord>> getKeys() {
        return Arrays.<UniqueKey<ExamRecord>>asList(Keys.KEY_EXAM_PRIMARY);
    }

    @Override
    public Exam as(String alias) {
        return new Exam(DSL.name(alias), this);
    }

    @Override
    public Exam as(Name alias) {
        return new Exam(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Exam rename(String name) {
        return new Exam(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Exam rename(Name name) {
        return new Exam(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<UUID, UUID, String, String, Double, LocalDate, LocalDate> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}