/*
 * This file is generated by jOOQ.
 */
package ch.teachu.teachuapi.generated.tables.records;


import ch.teachu.teachuapi.generated.tables.Grade;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;

import java.util.UUID;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GradeRecord extends UpdatableRecordImpl<GradeRecord> implements Record5<UUID, UUID, Double, String, UUID> {

    private static final long serialVersionUID = -397564594;

    /**
     * Setter for <code>teachu.grade.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>teachu.grade.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>teachu.grade.student_id</code>.
     */
    public void setStudentId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>teachu.grade.student_id</code>.
     */
    public UUID getStudentId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>teachu.grade.mark</code>.
     */
    public void setMark(Double value) {
        set(2, value);
    }

    /**
     * Getter for <code>teachu.grade.mark</code>.
     */
    public Double getMark() {
        return (Double) get(2);
    }

    /**
     * Setter for <code>teachu.grade.note</code>.
     */
    public void setNote(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>teachu.grade.note</code>.
     */
    public String getNote() {
        return (String) get(3);
    }

    /**
     * Setter for <code>teachu.grade.exam_id</code>.
     */
    public void setExamId(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>teachu.grade.exam_id</code>.
     */
    public UUID getExamId() {
        return (UUID) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<UUID, UUID, Double, String, UUID> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<UUID, UUID, Double, String, UUID> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Grade.GRADE.ID;
    }

    @Override
    public Field<UUID> field2() {
        return Grade.GRADE.STUDENT_ID;
    }

    @Override
    public Field<Double> field3() {
        return Grade.GRADE.MARK;
    }

    @Override
    public Field<String> field4() {
        return Grade.GRADE.NOTE;
    }

    @Override
    public Field<UUID> field5() {
        return Grade.GRADE.EXAM_ID;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public UUID component2() {
        return getStudentId();
    }

    @Override
    public Double component3() {
        return getMark();
    }

    @Override
    public String component4() {
        return getNote();
    }

    @Override
    public UUID component5() {
        return getExamId();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public UUID value2() {
        return getStudentId();
    }

    @Override
    public Double value3() {
        return getMark();
    }

    @Override
    public String value4() {
        return getNote();
    }

    @Override
    public UUID value5() {
        return getExamId();
    }

    @Override
    public GradeRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public GradeRecord value2(UUID value) {
        setStudentId(value);
        return this;
    }

    @Override
    public GradeRecord value3(Double value) {
        setMark(value);
        return this;
    }

    @Override
    public GradeRecord value4(String value) {
        setNote(value);
        return this;
    }

    @Override
    public GradeRecord value5(UUID value) {
        setExamId(value);
        return this;
    }

    @Override
    public GradeRecord values(UUID value1, UUID value2, Double value3, String value4, UUID value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GradeRecord
     */
    public GradeRecord() {
        super(Grade.GRADE);
    }

    /**
     * Create a detached, initialised GradeRecord
     */
    public GradeRecord(UUID id, UUID studentId, Double mark, String note, UUID examId) {
        super(Grade.GRADE);

        set(0, id);
        set(1, studentId);
        set(2, mark);
        set(3, note);
        set(4, examId);
    }
}
