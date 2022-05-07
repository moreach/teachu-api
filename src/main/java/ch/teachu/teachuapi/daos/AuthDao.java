package ch.teachu.teachuapi.daos;

import ch.teachu.teachuapi.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuthDao {
    public final static String ACCESS_EXPIRES = "accessExpires";
    public final static String REFRESH_EXPIRES = "refreshExpires";
    public final static String ROLE = "role";

    private LocalDateTime accessExpires;
    private LocalDateTime refreshExpires;
    private Role role;
}
