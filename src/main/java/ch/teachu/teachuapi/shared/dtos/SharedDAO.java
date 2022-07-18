package ch.teachu.teachuapi.shared.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SharedDAO {
    private String access;
    private String studentId;

    private String userId;
    private String role;
    private Boolean isParent;
}
