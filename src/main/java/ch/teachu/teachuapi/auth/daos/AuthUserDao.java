package ch.teachu.teachuapi.auth.daos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AuthUserDao {
    public static final String ID = "id";
    public static final String PASSWORD = "password";

    private UUID id;
    private String password;
}
