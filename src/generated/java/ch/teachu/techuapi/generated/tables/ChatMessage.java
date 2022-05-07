/*
 * This file is generated by jOOQ.
 */
package ch.teachu.techuapi.generated.tables;


import ch.teachu.teachuapi.enums.ChatState;
import ch.teachu.teachuapi.sql.generation.ChatStateConverter;
import ch.teachu.teachuapi.sql.generation.UuidConverter;
import ch.teachu.techuapi.generated.Keys;
import ch.teachu.techuapi.generated.Teachu;
import ch.teachu.techuapi.generated.tables.records.ChatMessageRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row6;
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
public class ChatMessage extends TableImpl<ChatMessageRecord> {

    private static final long serialVersionUID = -518248136;

    /**
     * The reference instance of <code>teachu.chat_message</code>
     */
    public static final ChatMessage CHAT_MESSAGE = new ChatMessage();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ChatMessageRecord> getRecordType() {
        return ChatMessageRecord.class;
    }

    /**
     * The column <code>teachu.chat_message.id</code>.
     */
    public final TableField<ChatMessageRecord, UUID> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BINARY(16).nullable(false), this, "", new UuidConverter());

    /**
     * The column <code>teachu.chat_message.chat_id</code>.
     */
    public final TableField<ChatMessageRecord, UUID> CHAT_ID = createField(DSL.name("chat_id"), org.jooq.impl.SQLDataType.BINARY(16), this, "", new UuidConverter());

    /**
     * The column <code>teachu.chat_message.message</code>.
     */
    public final TableField<ChatMessageRecord, String> MESSAGE = createField(DSL.name("message"), org.jooq.impl.SQLDataType.VARCHAR(1000), this, "");

    /**
     * The column <code>teachu.chat_message.user_id</code>.
     */
    public final TableField<ChatMessageRecord, UUID> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BINARY(16), this, "", new UuidConverter());

    /**
     * The column <code>teachu.chat_message.timestamp</code>.
     */
    public final TableField<ChatMessageRecord, LocalDateTime> TIMESTAMP = createField(DSL.name("timestamp"), org.jooq.impl.SQLDataType.LOCALDATETIME, this, "");

    /**
     * The column <code>teachu.chat_message.chat_state</code>.
     */
    public final TableField<ChatMessageRecord, ChatState> CHAT_STATE = createField(DSL.name("chat_state"), org.jooq.impl.SQLDataType.VARCHAR(100), this, "", new ChatStateConverter());

    /**
     * Create a <code>teachu.chat_message</code> table reference
     */
    public ChatMessage() {
        this(DSL.name("chat_message"), null);
    }

    /**
     * Create an aliased <code>teachu.chat_message</code> table reference
     */
    public ChatMessage(String alias) {
        this(DSL.name(alias), CHAT_MESSAGE);
    }

    /**
     * Create an aliased <code>teachu.chat_message</code> table reference
     */
    public ChatMessage(Name alias) {
        this(alias, CHAT_MESSAGE);
    }

    private ChatMessage(Name alias, Table<ChatMessageRecord> aliased) {
        this(alias, aliased, null);
    }

    private ChatMessage(Name alias, Table<ChatMessageRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> ChatMessage(Table<O> child, ForeignKey<O, ChatMessageRecord> key) {
        super(child, key, CHAT_MESSAGE);
    }

    @Override
    public Schema getSchema() {
        return Teachu.TEACHU;
    }

    @Override
    public UniqueKey<ChatMessageRecord> getPrimaryKey() {
        return Keys.KEY_CHAT_MESSAGE_PRIMARY;
    }

    @Override
    public List<UniqueKey<ChatMessageRecord>> getKeys() {
        return Arrays.<UniqueKey<ChatMessageRecord>>asList(Keys.KEY_CHAT_MESSAGE_PRIMARY);
    }

    @Override
    public ChatMessage as(String alias) {
        return new ChatMessage(DSL.name(alias), this);
    }

    @Override
    public ChatMessage as(Name alias) {
        return new ChatMessage(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ChatMessage rename(String name) {
        return new ChatMessage(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ChatMessage rename(Name name) {
        return new ChatMessage(name, null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<UUID, UUID, String, UUID, LocalDateTime, ChatState> fieldsRow() {
        return (Row6) super.fieldsRow();
    }
}
