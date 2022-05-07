/*
 * This file is generated by jOOQ.
 */
package ch.teachu.techuapi.generated.tables.records;


import ch.teachu.techuapi.generated.tables.Timetable;

import java.time.LocalTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TimetableRecord extends UpdatableRecordImpl<TimetableRecord> implements Record4<UUID, Integer, LocalTime, LocalTime> {

    private static final long serialVersionUID = 644532515;

    /**
     * Setter for <code>teachu.timetable.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>teachu.timetable.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>teachu.timetable.number</code>.
     */
    public void setNumber(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>teachu.timetable.number</code>.
     */
    public Integer getNumber() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>teachu.timetable.start_time</code>.
     */
    public void setStartTime(LocalTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>teachu.timetable.start_time</code>.
     */
    public LocalTime getStartTime() {
        return (LocalTime) get(2);
    }

    /**
     * Setter for <code>teachu.timetable.end_time</code>.
     */
    public void setEndTime(LocalTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>teachu.timetable.end_time</code>.
     */
    public LocalTime getEndTime() {
        return (LocalTime) get(3);
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
    public Row4<UUID, Integer, LocalTime, LocalTime> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<UUID, Integer, LocalTime, LocalTime> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Timetable.TIMETABLE.ID;
    }

    @Override
    public Field<Integer> field2() {
        return Timetable.TIMETABLE.NUMBER;
    }

    @Override
    public Field<LocalTime> field3() {
        return Timetable.TIMETABLE.START_TIME;
    }

    @Override
    public Field<LocalTime> field4() {
        return Timetable.TIMETABLE.END_TIME;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getNumber();
    }

    @Override
    public LocalTime component3() {
        return getStartTime();
    }

    @Override
    public LocalTime component4() {
        return getEndTime();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getNumber();
    }

    @Override
    public LocalTime value3() {
        return getStartTime();
    }

    @Override
    public LocalTime value4() {
        return getEndTime();
    }

    @Override
    public TimetableRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public TimetableRecord value2(Integer value) {
        setNumber(value);
        return this;
    }

    @Override
    public TimetableRecord value3(LocalTime value) {
        setStartTime(value);
        return this;
    }

    @Override
    public TimetableRecord value4(LocalTime value) {
        setEndTime(value);
        return this;
    }

    @Override
    public TimetableRecord values(UUID value1, Integer value2, LocalTime value3, LocalTime value4) {
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
     * Create a detached TimetableRecord
     */
    public TimetableRecord() {
        super(Timetable.TIMETABLE);
    }

    /**
     * Create a detached, initialised TimetableRecord
     */
    public TimetableRecord(UUID id, Integer number, LocalTime startTime, LocalTime endTime) {
        super(Timetable.TIMETABLE);

        set(0, id);
        set(1, number);
        set(2, startTime);
        set(3, endTime);
    }
}
