/*
 * This file is generated by jOOQ.
 */
package ch.teachu.techuapi.generated.tables.records;


import ch.teachu.techuapi.generated.tables.ChatMessage;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ChatMessageRecord extends UpdatableRecordImpl<ChatMessageRecord> implements Record6<UUID, UUID, String, UUID, LocalDateTime, String> {

    private static final long serialVersionUID = 1250680811;

    /**
     * Setter for <code>teachu.chat_message.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>teachu.chat_message.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>teachu.chat_message.chat_id</code>.
     */
    public void setChatId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>teachu.chat_message.chat_id</code>.
     */
    public UUID getChatId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>teachu.chat_message.message</code>.
     */
    public void setMessage(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>teachu.chat_message.message</code>.
     */
    public String getMessage() {
        return (String) get(2);
    }

    /**
     * Setter for <code>teachu.chat_message.user_id</code>.
     */
    public void setUserId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>teachu.chat_message.user_id</code>.
     */
    public UUID getUserId() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>teachu.chat_message.timestamp</code>.
     */
    public void setTimestamp(LocalDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>teachu.chat_message.timestamp</code>.
     */
    public LocalDateTime getTimestamp() {
        return (LocalDateTime) get(4);
    }

    /**
     * Setter for <code>teachu.chat_message.state</code>.
     */
    public void setState(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>teachu.chat_message.state</code>.
     */
    public String getState() {
        return (String) get(5);
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
    public Row6<UUID, UUID, String, UUID, LocalDateTime, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<UUID, UUID, String, UUID, LocalDateTime, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return ChatMessage.CHAT_MESSAGE.ID;
    }

    @Override
    public Field<UUID> field2() {
        return ChatMessage.CHAT_MESSAGE.CHAT_ID;
    }

    @Override
    public Field<String> field3() {
        return ChatMessage.CHAT_MESSAGE.MESSAGE;
    }

    @Override
    public Field<UUID> field4() {
        return ChatMessage.CHAT_MESSAGE.USER_ID;
    }

    @Override
    public Field<LocalDateTime> field5() {
        return ChatMessage.CHAT_MESSAGE.TIMESTAMP;
    }

    @Override
    public Field<String> field6() {
        return ChatMessage.CHAT_MESSAGE.STATE;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public UUID component2() {
        return getChatId();
    }

    @Override
    public String component3() {
        return getMessage();
    }

    @Override
    public UUID component4() {
        return getUserId();
    }

    @Override
    public LocalDateTime component5() {
        return getTimestamp();
    }

    @Override
    public String component6() {
        return getState();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public UUID value2() {
        return getChatId();
    }

    @Override
    public String value3() {
        return getMessage();
    }

    @Override
    public UUID value4() {
        return getUserId();
    }

    @Override
    public LocalDateTime value5() {
        return getTimestamp();
    }

    @Override
    public String value6() {
        return getState();
    }

    @Override
    public ChatMessageRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public ChatMessageRecord value2(UUID value) {
        setChatId(value);
        return this;
    }

    @Override
    public ChatMessageRecord value3(String value) {
        setMessage(value);
        return this;
    }

    @Override
    public ChatMessageRecord value4(UUID value) {
        setUserId(value);
        return this;
    }

    @Override
    public ChatMessageRecord value5(LocalDateTime value) {
        setTimestamp(value);
        return this;
    }

    @Override
    public ChatMessageRecord value6(String value) {
        setState(value);
        return this;
    }

    @Override
    public ChatMessageRecord values(UUID value1, UUID value2, String value3, UUID value4, LocalDateTime value5, String value6) {
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
     * Create a detached ChatMessageRecord
     */
    public ChatMessageRecord() {
        super(ChatMessage.CHAT_MESSAGE);
    }

    /**
     * Create a detached, initialised ChatMessageRecord
     */
    public ChatMessageRecord(UUID id, UUID chatId, String message, UUID userId, LocalDateTime timestamp, String state) {
        super(ChatMessage.CHAT_MESSAGE);

        set(0, id);
        set(1, chatId);
        set(2, message);
        set(3, userId);
        set(4, timestamp);
        set(5, state);
    }
}
