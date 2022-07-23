package ch.teachu.teachuapi.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDAO {
    private String userId;

    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String sex;
    private String language;
    private Boolean darkTheme;
    private String city;
    private String postalCode;
    private String street;
    private String phone;
    private String imageId;
}