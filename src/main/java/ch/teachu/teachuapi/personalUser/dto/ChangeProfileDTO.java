package ch.teachu.teachuapi.personalUser.dto;

import ch.teachu.teachuapi.enums.UserLanguage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeProfileDTO {
    private UserLanguage language;
    private boolean darkTheme;
    private String phone;
    private String profileImage;
    private String notes;
}
