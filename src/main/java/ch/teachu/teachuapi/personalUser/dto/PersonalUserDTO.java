package ch.teachu.teachuapi.personalUser.dto;

import ch.teachu.teachuapi.enums.UserLanguage;
import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.enums.UserSex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalUserDTO {

    public static final String EMAIL = "email";
    public static final String ROLE = "role";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String BIRTHDAY = "birthday";
    public static final String SEX = "sex";
    public static final String LANGUAGE = "language";
    public static final String DARK_THEME = "darkTheme";
    public static final String CITY = "city";
    public static final String POSTAL_CODE = "postalCode";
    public static final String STREET = "street";
    public static final String PHONE = "phone";
    public static final String PROFILE_IMAGE = "profileImage";
    public static final String NOTES = "notes";
    public static final String LAST_LOGIN = "lastLogin";
    public static final String CREATION_DATE = "creationDate";
    public static final String TERMINATE_DATE = "terminateDate";
    public static final String ACTIVE = "active";

    private String email;
    private UserRole role;
    private String firstName;
    private String lastName;
    private Date birthday;
    private UserSex sex;
    private UserLanguage language;
    private boolean darkTheme;
    private String city;
    private String postalCode;
    private String street;
    private String phone;
    private String profileImage;
    private String notes;
    private Date lastLogin;
    private Date creationDate;
    private Date terminationDate;
    private boolean active;
}
