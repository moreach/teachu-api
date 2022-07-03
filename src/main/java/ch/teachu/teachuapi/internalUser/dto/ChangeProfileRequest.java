package ch.teachu.teachuapi.internalUser.dto;

import ch.teachu.teachuapi.enums.UserLanguage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeProfileRequest {
    private UserLanguage language;
    private boolean darkTheme;
    private String phone;
}
