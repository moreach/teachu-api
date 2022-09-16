package ch.teachu.teachuapi.chat;

import ch.teachu.teachuapi.chat.dtos.*;
import ch.teachu.teachuapi.shared.AbstractService;
import ch.teachu.teachuapi.shared.dtos.SharedDAO;
import ch.teachu.teachuapi.shared.enums.ChatState;
import ch.teachu.teachuapi.shared.enums.UserRole;
import ch.teachu.teachuapi.shared.errorhandlig.UnauthorizedException;
import ch.teachu.teachuapi.sql.SQL;
import ch.teachu.teachuapi.user.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

            chatResponse.setLastMessage(getMessage(chatDAO.getId(), access));

            chatResponses.add(chatResponse);
        }

        return chatResponses;
    }

    public ch.teachu.teachuapi.shared.dtos.MessageResponse createChat(String access, ChatRequest chatRequest) {
        SharedDAO sharedDAO = authMinRole(access, UserRole.parent);

        ChatDAO chatDAO = new ChatDAO();
        chatDAO.setId(UUID.randomUUID().toString());
        chatDAO.setTitle(chatRequest.getTitle());
        chatDAO.setDescription(chatRequest.getDescription());
        chatDAO.setCreatorId(sharedDAO.getUserId());

        int count = SQL.insert("" +
                        "INSERT INTO chat ( " +
                        "       id, " +
                        "       title, " +
                        "       description, " +
                        "       creator_id)" +
                        "VALUES (" +
                        "       UUID_TO_BIN(-id), " +
                        "       -title, " +
                        "       -description, " +
                        "       UUID_TO_BIN(-creatorId))",
                chatDAO);

        if (count == 0) {
            throw new RuntimeException("Failed to create chat");
        }

        chatRequest.getMemberIds().add(sharedDAO.getUserId());

        addUsers(chatDAO.getId(), chatRequest.getMemberIds());

        return new ch.teachu.teachuapi.shared.dtos.MessageResponse("Successfully created Chat");
    }

    public ch.teachu.teachuapi.shared.dtos.MessageResponse updateChat(String access, String chatId, ChatRequest chatRequest) {
        SharedDAO sharedDAO = authMinRole(access, UserRole.parent);

        checkIfUserHasChat(sharedDAO.getUserId(), chatId);

        ChatDAO chatDAO = new ChatDAO();
        chatDAO.setId(chatId);
        chatDAO.setTitle(chatRequest.getTitle());
        chatDAO.setDescription(chatRequest.getDescription());

        int count = SQL.update("" +
                        "UPDATE chat " +
                        "SET    title = -title, " +
                        "       description = -description " +
                        "WHERE  id = UUID_TO_BIN(-id)",
                chatDAO);


        if (count == 0) {
            throw new RuntimeException("Failed to update chat");
        }

        count = SQL.delete("" +
                        "DELETE FROM chat_user " +
                        "WHERE  chat_id = UUID_TO_BIN(-id) ",
                chatDAO);

        if (count == 0) {
            throw new RuntimeException("Failed to update chat users");
        }

        addUsers(chatId, chatRequest.getMemberIds());

        return new ch.teachu.teachuapi.shared.dtos.MessageResponse("Successfully updated Chat");
    }

    public void addUsers(String chatId, List<String> memberIds) {

        ChatDAO chatDAO = new ChatDAO();
        chatDAO.setId(chatId);

        for (String memberId : memberIds) {
            chatDAO.setCreatorId(memberId);

            int count = SQL.insert("" +
                            "INSERT INTO chat_user ( " +
                            "       chat_id, " +
                            "       user_id)" +
                            "VALUES (" +
                            "       UUID_TO_BIN(-id), " +
                            "       UUID_TO_BIN(-creatorId))",
                    chatDAO);

            if (count == 0) {
                throw new RuntimeException("Failed to add user to chat");
            }
        }
    }

    public void checkIfUserHasChat(String userId, String chatId) {

        ChatDAO chatDAO = new ChatDAO();
        chatDAO.setId(chatId);
        chatDAO.setCreatorId(userId);

        SQL.select("" +
                        "SELECT BIN_TO_UUID(chat_id) " +
                        "FROM   chat_user " +
                        "WHERE  user_id = UUID_TO_BIN(-creatorId) " +
                        "AND    chat_id = UUID_TO_BIN(-id) " +
                        "INTO   :title ",
                chatDAO,
                chatDAO);

        if (chatDAO.getTitle() == null) {
            throw new UnauthorizedException("You are not in this chat");
        }
    }

    public List<MessageResponse> getMessages(String access, String chatId) {
        SharedDAO sharedDAO = authMinRole(access, UserRole.parent);

        checkIfUserHasChat(sharedDAO.getUserId(), chatId);

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
                        "WHERE  chat_id = UUID_TO_BIN(-id) " +
                        "INTO   :id, " +
                        "       :userId, " +
                        "       :message, " +
                        "       :timestamp, " +
                        "       :chatState ",
                messageDAOs,
                MessageDAO.class,
                chatDAO);

        List<MessageResponse> messageResponses = new ArrayList<>();

        for (MessageDAO messageDAO : messageDAOs) {
            if (messageDAO.getChatState().equals(ChatState.unread.name())) {
                if (!messageDAO.getUserId().equals(sharedDAO.getUserId())) {
                    messageDAO.setChatState(ChatState.read.name());

                    int count = SQL.update("" +
                                    "UPDATE chat_message " +
                                    "SET    chat_state = -chatState " +
                                    "WHERE  id = UUID_TO_BIN(-id) ",
                            messageDAO);

                    if (count == 0) {
                        throw new RuntimeException("Failed to update message state");
                    }
                }
            }

            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setMessage(messageDAO.getMessage());
            messageResponse.setUser(userService.getExternalUser(access, messageDAO.getUserId()));
            messageResponse.setTimestamp(messageDAO.getTimestamp());
            messageResponse.setChatState(ChatState.valueOf(messageDAO.getChatState()));

            messageResponses.add(messageResponse);
        }

        messageResponses.sort((o1, o2) -> (int) (o1.getTimestamp().getTime() - o2.getTimestamp().getTime()));

        return messageResponses;
    }

    public ch.teachu.teachuapi.shared.dtos.MessageResponse createMessage(String access, MessageRequest messageRequest) {
        SharedDAO sharedDAO = authMinRole(access, UserRole.parent);

        checkIfUserHasChat(sharedDAO.getUserId(), messageRequest.getChatId());

        MessageDAO messageDAO = new MessageDAO();
        messageDAO.setId(UUID.randomUUID().toString());
        messageDAO.setChatId(messageRequest.getChatId());
        messageDAO.setUserId(sharedDAO.getUserId());
        messageDAO.setMessage(messageRequest.getMessage());
        messageDAO.setTimestamp(new Date());
        messageDAO.setChatState(ChatState.unread.name());

        int count = SQL.insert("" +
                        "INSERT INTO chat_message (" +
                        "       id, " +
                        "       chat_id, " +
                        "       user_id, " +
                        "       message, " +
                        "       timestamp, " +
                        "       chat_state)" +
                        "VALUES (" +
                        "       UUID_TO_BIN(-id), " +
                        "       UUID_TO_BIN(-chatId), " +
                        "       UUID_TO_BIN(-userId), " +
                        "       -message, " +
                        "       -timestamp, " +
                        "       -chatState)",
                messageDAO);

        if (count == 0) {
            throw new RuntimeException("Failed to create message");
        }

        return new ch.teachu.teachuapi.shared.dtos.MessageResponse("Successfully created message");
    }

    private MessageResponse getMessage(String chatId, String access) {
        ChatDAO chatDAO = new ChatDAO();
        chatDAO.setId(chatId);

        MessageDAO messageDAO = new MessageDAO();

        SQL.select("" +
                        "SELECT BIN_TO_UUID(id), " +
                        "       BIN_TO_UUID(user_id), " +
                        "       message, " +
                        "       timestamp, " +
                        "       chat_state " +
                        "FROM   chat_message " +
                        "WHERE  chat_id = UUID_TO_BIN(-id) " +
                        "ORDER BY timestamp DESC " +
                        "INTO   :id, " +
                        "       :userId, " +
                        "       :message, " +
                        "       :timestamp, " +
                        "       :chatState ",
                messageDAO,
                chatDAO);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage(messageDAO.getMessage());
        messageResponse.setUser(userService.getExternalUser(access, messageDAO.getUserId()));
        messageResponse.setTimestamp(messageDAO.getTimestamp());
        messageResponse.setChatState(ChatState.valueOf(messageDAO.getChatState()));

        return messageResponse;
    }
}
