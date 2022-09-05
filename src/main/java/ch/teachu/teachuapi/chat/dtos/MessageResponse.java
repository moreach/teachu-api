package ch.teachu.teachuapi.chat.dtos;

import ch.teachu.teachuapi.shared.enums.ChatState;
import ch.teachu.teachuapi.user.dtos.ExternalUserResponse;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

  private String message;
  private ExternalUserResponse user;
  private Date timestamp;
  private ChatState chatState;
}
