package ch.teachu.teachuapi.user.dtos;

import ch.teachu.teachuapi.shared.enums.UserRole;
import ch.teachu.teachuapi.shared.enums.UserSex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExternalUserResponse {
    private String email;
    private UserRole role;
    private String firstName;
    private String lastName;
    private Date birthday;
    private UserSex sex;
    private String city;
    // todo profile img
}