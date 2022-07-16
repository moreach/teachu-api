package ch.teachu.teachuapi.authentication.dtos;

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
    private String id;
    private String password;
    private String access;
    private String refresh;
    private Date accessExpires;
    private Date refreshExpires;
}