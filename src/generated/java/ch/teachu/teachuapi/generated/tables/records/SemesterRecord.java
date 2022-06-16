/*
 * This file is generated by jOOQ.
 */
package ch.teachu.teachuapi.generated.tables.records;


import ch.teachu.teachuapi.generated.tables.Semester;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;

import java.time.LocalDateTime;
import java.util.UUID;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SemesterRecord extends UpdatableRecordImpl<SemesterRecord> implements Record4<UUID, String, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = 813884289;

    /**
     * Setter for <code>teachu.semester.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>teachu.semester.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>teachu.semester.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>teachu.semester.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>teachu.semester.from</code>.
     */
    public void setFrom(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>teachu.semester.from</code>.
     */
    public LocalDateTime getFrom() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>teachu.semester.to</code>.
     */
    public void setTo(LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>teachu.semester.to</code>.
     */
    public LocalDateTime getTo() {
        return (LocalDateTime) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<UUID, String, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<UUID, String, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Semester.SEMESTER.ID;
    }

    @Override
    public Field<String> field2() {
        return Semester.SEMESTER.NAME;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return Semester.SEMESTER.FROM;
    }

    @Override
    public Field<LocalDateTime> field4() {
        return Semester.SEMESTER.TO;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public LocalDateTime component3() {
        return getFrom();
    }

    @Override
    public LocalDateTime component4() {
        return getTo();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public LocalDateTime value3() {
        return getFrom();
    }

    @Override
    public LocalDateTime value4() {
        return getTo();
    }

    @Override
    public SemesterRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public SemesterRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public SemesterRecord value3(LocalDateTime value) {
        setFrom(value);
        return this;
    }

    @Override
    public SemesterRecord value4(LocalDateTime value) {
        setTo(value);
        return this;
    }

    @Override
    public SemesterRecord values(UUID value1, String value2, LocalDateTime value3, LocalDateTime value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SemesterRecord
     */
    public SemesterRecord() {
        super(Semester.SEMESTER);
    }

    /**
     * Create a detached, initialised SemesterRecord
     */
    public SemesterRecord(UUID id, String name, LocalDateTime from, LocalDateTime to) {
        super(Semester.SEMESTER);

        set(0, id);
        set(1, name);
        set(2, from);
        set(3, to);
    }
}