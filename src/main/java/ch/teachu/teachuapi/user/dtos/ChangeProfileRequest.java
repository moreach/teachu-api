package ch.teachu.teachuapi.user.dtos;

import ch.teachu.teachuapi.parent.enums.UserLanguage;
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
