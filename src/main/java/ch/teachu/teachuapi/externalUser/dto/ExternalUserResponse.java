package ch.teachu.teachuapi.externalUser.dto;

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
public class ExternalUserResponse {

    public static final String EMAIL = "email";
    public static final String ROLE = "role";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String BIRTHDAY = "birthday";
    public static final String SEX = "sex";
    public static final String CITY = "city";
    public static final String PROFILE_IMAGE = "profileImage";

    private String email;
    private UserRole role;
    private String firstName;
    private String lastName;
    private Date birthday;
    private UserSex sex;
    private String city;
    private String profileImage;
}
