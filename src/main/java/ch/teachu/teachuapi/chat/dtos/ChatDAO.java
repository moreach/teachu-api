package ch.teachu.teachuapi.chat.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatDAO {
    private String id;
    private String title;
    private String description;
    private String creatorId;
    private List<UserDAO> userDAOs;
}
