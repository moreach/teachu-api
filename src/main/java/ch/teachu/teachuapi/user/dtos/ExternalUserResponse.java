package ch.teachu.teachuapi.user.dtos;

import ch.teachu.teachuapi.shared.enums.UserRole;
import ch.teachu.teachuapi.shared.enums.UserSex;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ExternalUserResponse {
    private String id;
    private String email;
    private UserRole role;
    private String firstName;
    private String lastName;
    private Date birthday;
    private UserSex sex;
    private String city;
    private String imageId;
}