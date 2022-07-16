package ch.teachu.teachuapi.user.dtos;

import ch.teachu.teachuapi.parent.enums.UserLanguage;
import ch.teachu.teachuapi.parent.enums.UserRole;
import ch.teachu.teachuapi.parent.enums.UserSex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InternalUserResponse {
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
    // todo profile image
}