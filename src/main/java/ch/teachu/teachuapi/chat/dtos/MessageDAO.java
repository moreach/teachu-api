package ch.teachu.teachuapi.chat.dtos;

import ch.teachu.teachuapi.shared.enums.ChatState;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
