/*
 * This file is generated by jOOQ.
 */
package ch.teachu.teachuapi.generated.tables;


import ch.teachu.teachuapi.enums.UserLanguage;
import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.enums.UserSex;
import ch.teachu.teachuapi.generated.Keys;
import ch.teachu.teachuapi.generated.Teachu;
import ch.teachu.teachuapi.generated.tables.records.UserRecord;
import ch.teachu.teachuapi.sql.generation.UserLanguageConverter;
import ch.teachu.teachuapi.sql.generation.UserRoleConverter;
import ch.teachu.teachuapi.sql.generation.UserSexConverter;
import ch.teachu.teachuapi.sql.generation.UuidConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row20;
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
public class User extends TableImpl<UserRecord> {

    private static final long serialVersionUID = -851599621;

    /**
     * The reference instance of <code>teachu.user</code>
     */
    public static final User USER = new User();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserRecord> getRecordType() {
        return UserRecord.class;
    }

    /**
     * The column <code>teachu.user.id</code>.
     */
    public final TableField<UserRecord, UUID> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BINARY(16).nullable(false), this, "", new UuidConverter());

    /**
     * The column <code>teachu.user.email</code>.
     */
    public final TableField<UserRecord, String> EMAIL = createField(DSL.name("email"), org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>teachu.user.password</code>.
     */
    public final TableField<UserRecord, String> PASSWORD = createField(DSL.name("password"), org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>teachu.user.role</code>.
     */
    public final TableField<UserRecord, UserRole> ROLE = createField(DSL.name("role"), org.jooq.impl.SQLDataType.VARCHAR(100), this, "", new UserRoleConverter());

    /**
     * The column <code>teachu.user.first_name</code>.
     */
    public final TableField<UserRecord, String> FIRST_NAME = createField(DSL.name("first_name"), org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>teachu.user.last_name</code>.
     */
    public final TableField<UserRecord, String> LAST_NAME = createField(DSL.name("last_name"), org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>teachu.user.birthday</code>.
     */
    public final TableField<UserRecord, LocalDate> BIRTHDAY = createField(DSL.name("birthday"), org.jooq.impl.SQLDataType.LOCALDATE, this, "");

    /**
     * The column <code>teachu.user.sex</code>.
     */
    public final TableField<UserRecord, UserSex> SEX = createField(DSL.name("sex"), org.jooq.impl.SQLDataType.VARCHAR(100), this, "", new UserSexConverter());

    /**
     * The column <code>teachu.user.language</code>.
     */
    public final TableField<UserRecord, UserLanguage> LANGUAGE = createField(DSL.name("language"), org.jooq.impl.SQLDataType.VARCHAR(100), this, "", new UserLanguageConverter());

    /**
     * The column <code>teachu.user.dark_theme</code>.
     */
    public final TableField<UserRecord, Boolean> DARK_THEME = createField(DSL.name("dark_theme"), org.jooq.impl.SQLDataType.BOOLEAN, this, "");

    /**
     * The column <code>teachu.user.city</code>.
     */
    public final TableField<UserRecord, String> CITY = createField(DSL.name("city"), org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>teachu.user.postal_code</code>.
     */
    public final TableField<UserRecord, String> POSTAL_CODE = createField(DSL.name("postal_code"), org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>teachu.user.street</code>.
     */
    public final TableField<UserRecord, String> STREET = createField(DSL.name("street"), org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>teachu.user.phone</code>.
     */
    public final TableField<UserRecord, String> PHONE = createField(DSL.name("phone"), org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>teachu.user.profile_img</code>.
     */
    public final TableField<UserRecord, String> PROFILE_IMG = createField(DSL.name("profile_img"), org.jooq.impl.SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>teachu.user.notes</code>.
     */
    public final TableField<UserRecord, String> NOTES = createField(DSL.name("notes"), org.jooq.impl.SQLDataType.VARCHAR(1000), this, "");

    /**
     * The column <code>teachu.user.last_login</code>.
     */
    public final TableField<UserRecord, LocalDateTime> LAST_LOGIN = createField(DSL.name("last_login"), org.jooq.impl.SQLDataType.LOCALDATETIME, this, "");

    /**
     * The column <code>teachu.user.creation_date</code>.
     */
    public final TableField<UserRecord, LocalDate> CREATION_DATE = createField(DSL.name("creation_date"), org.jooq.impl.SQLDataType.LOCALDATE, this, "");

    /**
     * The column <code>teachu.user.termination_date</code>.
     */
    public final TableField<UserRecord, LocalDate> TERMINATION_DATE = createField(DSL.name("termination_date"), org.jooq.impl.SQLDataType.LOCALDATE, this, "");

    /**
     * The column <code>teachu.user.active</code>.
     */
    public final TableField<UserRecord, Boolean> ACTIVE = createField(DSL.name("active"), org.jooq.impl.SQLDataType.BOOLEAN, this, "");

    /**
     * Create a <code>teachu.user</code> table reference
     */
    public User() {
        this(DSL.name("user"), null);
    }

    /**
     * Create an aliased <code>teachu.user</code> table reference
     */
    public User(String alias) {
        this(DSL.name(alias), USER);
    }

    /**
     * Create an aliased <code>teachu.user</code> table reference
     */
    public User(Name alias) {
        this(alias, USER);
    }

    private User(Name alias, Table<UserRecord> aliased) {
        this(alias, aliased, null);
    }

    private User(Name alias, Table<UserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> User(Table<O> child, ForeignKey<O, UserRecord> key) {
        super(child, key, USER);
    }

    @Override
    public Schema getSchema() {
        return Teachu.TEACHU;
    }

    @Override
    public UniqueKey<UserRecord> getPrimaryKey() {
        return Keys.KEY_USER_PRIMARY;
    }

    @Override
    public List<UniqueKey<UserRecord>> getKeys() {
        return Arrays.<UniqueKey<UserRecord>>asList(Keys.KEY_USER_PRIMARY);
    }

    @Override
    public User as(String alias) {
        return new User(DSL.name(alias), this);
    }

    @Override
    public User as(Name alias) {
        return new User(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(String name) {
        return new User(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(Name name) {
        return new User(name, null);
    }

    // -------------------------------------------------------------------------
    // Row20 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row20<UUID, String, String, UserRole, String, String, LocalDate, UserSex, UserLanguage, Boolean, String, String, String, String, String, String, LocalDateTime, LocalDate, LocalDate, Boolean> fieldsRow() {
        return (Row20) super.fieldsRow();
    }
}
