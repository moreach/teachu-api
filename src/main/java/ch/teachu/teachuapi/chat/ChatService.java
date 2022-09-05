package ch.teachu.teachuapi.chat;

import ch.teachu.teachuapi.chat.dtos.ChatDAO;
import ch.teachu.teachuapi.chat.dtos.ChatResponse;
import ch.teachu.teachuapi.chat.dtos.MessageDAO;
import ch.teachu.teachuapi.chat.dtos.MessageResponse;
import ch.teachu.teachuapi.chat.dtos.UserDAO;
import ch.teachu.teachuapi.shared.AbstractService;
import ch.teachu.teachuapi.shared.dtos.SharedDAO;
import ch.teachu.teachuapi.shared.enums.ChatState;
import ch.teachu.teachuapi.shared.enums.UserRole;
import ch.teachu.teachuapi.sql.SQL;
import ch.teachu.teachuapi.user.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ChatService extends AbstractService {

  private final UserService userService;

  public ChatService(UserService userService) {
    this.userService = userService;
  }

  public List<ChatResponse> getChats(String access) {
    SharedDAO sharedDAO = authMinRole(access, UserRole.parent);

    List<ChatDAO> chatDAOs = new ArrayList<>();

    SQL.select("" +
            "SELECT BIN_TO_UUID(c.id), " +
            "       c.title, " +
            "       c.description," +
            "       BIN_TO_UUID(c.creator_id) " +
            "FROM   chat c " +
            "INNER JOIN chat_user cu ON c.id = cu.chat_id " +
            "WHERE  cu.user_id = UUID_TO_BIN(-userId) " +
            "INTO   :id, " +
            "       :title, " +
            "       :description, " +
            "       :creatorId",
        chatDAOs,
        ChatDAO.class,
        sharedDAO
    );

    for (ChatDAO chatDAO : chatDAOs) {
      List<UserDAO> userDAOs = new ArrayList<>();

      SQL.select("" +
              "SELECT BIN_TO_UUID(user_id) " +
              "FROM   chat_user " +
              "WHERE  chat_id = UUID_TO_BIN(-id) " +
              "INTO   :id",
          userDAOs,
          UserDAO.class,
          chatDAO);

      chatDAO.setUserDAOs(userDAOs);
    }

    List<ChatResponse> chatResponses = new ArrayList<>();

    for (ChatDAO chatDAO : chatDAOs) {
      ChatResponse chatResponse = new ChatResponse();
      chatResponse.setId(chatDAO.getId());
      chatResponse.setTitle(chatDAO.getTitle());
      chatResponse.setDescription(chatDAO.getDescription());
      chatResponse.setCreator(userService.getExternalUser(access, chatDAO.getCreatorId()));
      chatResponse.setMembers(new ArrayList<>());

      for (UserDAO userDAO : chatDAO.getUserDAOs()) {
        chatResponse.getMembers().add(userService.getExternalUser(access, userDAO.getId()));
      }

      chatResponses.add(chatResponse);
    }

    return chatResponses;
  }

  public List<MessageResponse> getMessages(String access, String chatId) {
    SharedDAO sharedDAO = authMinRole(access, UserRole.parent);

    ChatDAO chatDAO = new ChatDAO();
    chatDAO.setId(chatId);

    List<MessageDAO> messageDAOs = new ArrayList<>();

    SQL.select("" +
            "SELECT BIN_TO_UUID(id), " +
            "       BIN_TO_UUID(user_id), " +
            "       message, " +
            "       timestamp, " +
            "       chat_state " +
            "FROM   chat_message " +
            "WHERE  chat_id = UUID_TO_BIN(-id) ",
        messageDAOs,
        MessageDAO.class,
        chatDAO);

    List<MessageResponse> messageResponses = new ArrayList<>();

    for (MessageDAO messageDAO : messageDAOs) {
      MessageResponse messageResponse = new MessageResponse();
      messageResponse.setMessage(messageDAO.getMessage());
      messageResponse.setUser(userService.getExternalUser(access, messageDAO.getUserId()));
      messageResponse.setTimestamp(messageDAO.getTimestamp());
      messageResponse.setChatState(ChatState.valueOf(messageDAO.getChatState()));

      messageResponses.add(messageResponse);
    }

    return messageResponses;
  }
}
