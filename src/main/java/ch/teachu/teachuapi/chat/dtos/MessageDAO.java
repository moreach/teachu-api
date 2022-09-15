package ch.teachu.teachuapi.chat.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDAO {
    private String id;
    private String chatId;
    private String userId;
    private String message;
    private Date timestamp;
    private String chatState;
}
