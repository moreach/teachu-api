/*
 * This file is generated by jOOQ.
 */
package ch.teachu.techuapi.generated.tables.records;


import ch.teachu.techuapi.generated.tables.Token;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TokenRecord extends UpdatableRecordImpl<TokenRecord> implements Record5<UUID, String, String, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = 281184138;

    /**
     * Setter for <code>teachu.token.user_id</code>.
     */
    public void setUserId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>teachu.token.user_id</code>.
     */
    public UUID getUserId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>teachu.token.access</code>.
     */
    public void setAccess(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>teachu.token.access</code>.
     */
    public String getAccess() {
        return (String) get(1);
    }

    /**
     * Setter for <code>teachu.token.refresh</code>.
     */
    public void setRefresh(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>teachu.token.refresh</code>.
     */
    public String getRefresh() {
        return (String) get(2);
    }

    /**
     * Setter for <code>teachu.token.access_expires</code>.
     */
    public void setAccessExpires(LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>teachu.token.access_expires</code>.
     */
    public LocalDateTime getAccessExpires() {
        return (LocalDateTime) get(3);
    }

    /**
     * Setter for <code>teachu.token.refresh_expires</code>.
     */
    public void setRefreshExpires(LocalDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>teachu.token.refresh_expires</code>.
     */
    public LocalDateTime getRefreshExpires() {
        return (LocalDateTime) get(4);
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
    public Row5<UUID, String, String, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<UUID, String, String, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Token.TOKEN.USER_ID;
    }

    @Override
    public Field<String> field2() {
        return Token.TOKEN.ACCESS;
    }

    @Override
    public Field<String> field3() {
        return Token.TOKEN.REFRESH;
    }

    @Override
    public Field<LocalDateTime> field4() {
        return Token.TOKEN.ACCESS_EXPIRES;
    }

    @Override
    public Field<LocalDateTime> field5() {
        return Token.TOKEN.REFRESH_EXPIRES;
    }

    @Override
    public UUID component1() {
        return getUserId();
    }

    @Override
    public String component2() {
        return getAccess();
    }

    @Override
    public String component3() {
        return getRefresh();
    }

    @Override
    public LocalDateTime component4() {
        return getAccessExpires();
    }

    @Override
    public LocalDateTime component5() {
        return getRefreshExpires();
    }

    @Override
    public UUID value1() {
        return getUserId();
    }

    @Override
    public String value2() {
        return getAccess();
    }

    @Override
    public String value3() {
        return getRefresh();
    }

    @Override
    public LocalDateTime value4() {
        return getAccessExpires();
    }

    @Override
    public LocalDateTime value5() {
        return getRefreshExpires();
    }

    @Override
    public TokenRecord value1(UUID value) {
        setUserId(value);
        return this;
    }

    @Override
    public TokenRecord value2(String value) {
        setAccess(value);
        return this;
    }

    @Override
    public TokenRecord value3(String value) {
        setRefresh(value);
        return this;
    }

    @Override
    public TokenRecord value4(LocalDateTime value) {
        setAccessExpires(value);
        return this;
    }

    @Override
    public TokenRecord value5(LocalDateTime value) {
        setRefreshExpires(value);
        return this;
    }

    @Override
    public TokenRecord values(UUID value1, String value2, String value3, LocalDateTime value4, LocalDateTime value5) {
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
     * Create a detached TokenRecord
     */
    public TokenRecord() {
        super(Token.TOKEN);
    }

    /**
     * Create a detached, initialised TokenRecord
     */
    public TokenRecord(UUID userId, String access, String refresh, LocalDateTime accessExpires, LocalDateTime refreshExpires) {
        super(Token.TOKEN);

        set(0, userId);
        set(1, access);
        set(2, refresh);
        set(3, accessExpires);
        set(4, refreshExpires);
    }
}
