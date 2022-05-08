package ch.teachu.teachuapi.daos;

import ch.teachu.teachuapi.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AuthDAO {
    public final static String ACCESS_EXPIRES = "accessExpires";
    public final static String REFRESH_EXPIRES = "refreshExpires";
    public final static String USER_ID = "userId";
    public final static String ROLE = "role";

    private LocalDateTime accessExpires;
    private LocalDateTime refreshExpires;
    private UUID userId;
    private UserRole role;
}
