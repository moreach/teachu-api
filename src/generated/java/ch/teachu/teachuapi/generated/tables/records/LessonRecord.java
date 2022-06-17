/*
 * This file is generated by jOOQ.
 */
package ch.teachu.teachuapi.generated.tables.records;


import ch.teachu.teachuapi.enums.Weekday;
import ch.teachu.teachuapi.generated.tables.Lesson;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;

import java.time.LocalTime;
import java.util.UUID;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LessonRecord extends UpdatableRecordImpl<LessonRecord> implements Record6<UUID, UUID, LocalTime, LocalTime, Weekday, byte[]> {

    private static final long serialVersionUID = -354741109;

    /**
     * Setter for <code>teachu.lesson.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>teachu.lesson.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>teachu.lesson.school_class_subject_id</code>.
     */
    public void setSchoolClassSubjectId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>teachu.lesson.school_class_subject_id</code>.
     */
    public UUID getSchoolClassSubjectId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>teachu.lesson.start_time</code>.
     */
    public void setStartTime(LocalTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>teachu.lesson.start_time</code>.
     */
    public LocalTime getStartTime() {
        return (LocalTime) get(2);
    }

    /**
     * Setter for <code>teachu.lesson.end_time</code>.
     */
    public void setEndTime(LocalTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>teachu.lesson.end_time</code>.
     */
    public LocalTime getEndTime() {
        return (LocalTime) get(3);
    }

    /**
     * Setter for <code>teachu.lesson.weekday</code>.
     */
    public void setWeekday(Weekday value) {
        set(4, value);
    }

    /**
     * Getter for <code>teachu.lesson.weekday</code>.
     */
    public Weekday getWeekday() {
        return (Weekday) get(4);
    }

    /**
     * Setter for <code>teachu.lesson.room</code>.
     */
    public void setRoom(byte[] value) {
        set(5, value);
    }

    /**
     * Getter for <code>teachu.lesson.room</code>.
     */
    public byte[] getRoom() {
        return (byte[]) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<UUID, UUID, LocalTime, LocalTime, Weekday, byte[]> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<UUID, UUID, LocalTime, LocalTime, Weekday, byte[]> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Lesson.LESSON.ID;
    }

    @Override
    public Field<UUID> field2() {
        return Lesson.LESSON.SCHOOL_CLASS_SUBJECT_ID;
    }

    @Override
    public Field<LocalTime> field3() {
        return Lesson.LESSON.START_TIME;
    }

    @Override
    public Field<LocalTime> field4() {
        return Lesson.LESSON.END_TIME;
    }

    @Override
    public Field<Weekday> field5() {
        return Lesson.LESSON.WEEKDAY;
    }

    @Override
    public Field<byte[]> field6() {
        return Lesson.LESSON.ROOM;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public UUID component2() {
        return getSchoolClassSubjectId();
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
    public Weekday component5() {
        return getWeekday();
    }

    @Override
    public byte[] component6() {
        return getRoom();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public UUID value2() {
        return getSchoolClassSubjectId();
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
    public Weekday value5() {
        return getWeekday();
    }

    @Override
    public byte[] value6() {
        return getRoom();
    }

    @Override
    public LessonRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public LessonRecord value2(UUID value) {
        setSchoolClassSubjectId(value);
        return this;
    }

    @Override
    public LessonRecord value3(LocalTime value) {
        setStartTime(value);
        return this;
    }

    @Override
    public LessonRecord value4(LocalTime value) {
        setEndTime(value);
        return this;
    }

    @Override
    public LessonRecord value5(Weekday value) {
        setWeekday(value);
        return this;
    }

    @Override
    public LessonRecord value6(byte[] value) {
        setRoom(value);
        return this;
    }

    @Override
    public LessonRecord values(UUID value1, UUID value2, LocalTime value3, LocalTime value4, Weekday value5, byte[] value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LessonRecord
     */
    public LessonRecord() {
        super(Lesson.LESSON);
    }

    /**
     * Create a detached, initialised LessonRecord
     */
    public LessonRecord(UUID id, UUID schoolClassSubjectId, LocalTime startTime, LocalTime endTime, Weekday weekday, byte[] room) {
        super(Lesson.LESSON);

        set(0, id);
        set(1, schoolClassSubjectId);
        set(2, startTime);
        set(3, endTime);
        set(4, weekday);
        set(5, room);
    }
}
