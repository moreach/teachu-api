/*
 * This file is generated by jOOQ.
 */
package ch.teachu.teachuapi.generated.tables;


import ch.teachu.teachuapi.generated.Keys;
import ch.teachu.teachuapi.generated.Teachu;
import ch.teachu.teachuapi.generated.tables.records.TokenRecord;
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
public class Token extends TableImpl<TokenRecord> {

    private static final long serialVersionUID = -764969596;

    /**
     * The reference instance of <code>teachu.token</code>
     */
    public static final Token TOKEN = new Token();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TokenRecord> getRecordType() {
        return TokenRecord.class;
    }

    /**
     * The column <code>teachu.token.user_id</code>.
     */
    public final TableField<TokenRecord, UUID> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BINARY(16).nullable(false), this, "", new UuidConverter());

    /**
     * The column <code>teachu.token.access</code>.
     */
    public final TableField<TokenRecord, String> ACCESS = createField(DSL.name("access"), org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>teachu.token.refresh</code>.
     */
    public final TableField<TokenRecord, String> REFRESH = createField(DSL.name("refresh"), org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>teachu.token.access_expires</code>.
     */
    public final TableField<TokenRecord, LocalDateTime> ACCESS_EXPIRES = createField(DSL.name("access_expires"), org.jooq.impl.SQLDataType.LOCALDATETIME, this, "");

    /**
     * The column <code>teachu.token.refresh_expires</code>.
     */
    public final TableField<TokenRecord, LocalDateTime> REFRESH_EXPIRES = createField(DSL.name("refresh_expires"), org.jooq.impl.SQLDataType.LOCALDATETIME, this, "");

    /**
     * Create a <code>teachu.token</code> table reference
     */
    public Token() {
        this(DSL.name("token"), null);
    }

    /**
     * Create an aliased <code>teachu.token</code> table reference
     */
    public Token(String alias) {
        this(DSL.name(alias), TOKEN);
    }

    /**
     * Create an aliased <code>teachu.token</code> table reference
     */
    public Token(Name alias) {
        this(alias, TOKEN);
    }

    private Token(Name alias, Table<TokenRecord> aliased) {
        this(alias, aliased, null);
    }

    private Token(Name alias, Table<TokenRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Token(Table<O> child, ForeignKey<O, TokenRecord> key) {
        super(child, key, TOKEN);
    }

    @Override
    public Schema getSchema() {
        return Teachu.TEACHU;
    }

    @Override
    public UniqueKey<TokenRecord> getPrimaryKey() {
        return Keys.KEY_TOKEN_PRIMARY;
    }

    @Override
    public List<UniqueKey<TokenRecord>> getKeys() {
        return Arrays.<UniqueKey<TokenRecord>>asList(Keys.KEY_TOKEN_PRIMARY);
    }

    @Override
    public Token as(String alias) {
        return new Token(DSL.name(alias), this);
    }

    @Override
    public Token as(Name alias) {
        return new Token(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Token rename(String name) {
        return new Token(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Token rename(Name name) {
        return new Token(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<UUID, String, String, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}