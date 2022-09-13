package ch.teachu.teachuapi.chat;

import ch.teachu.teachuapi.shared.errorhandlig.UnauthorizedException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChatHandler extends TextWebSocketHandler {

    private List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        HttpHeaders headers = session.getHandshakeHeaders();

        List<String> access = headers.get("access");

        if (access == null || access.size() != 1) {
            throw new UnauthorizedException("Access token is missing");
        }


        sessions.add(session);
        sessions.forEach(System.out::println);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        System.out.println(message.getPayload());
        sendMessage(session);
    }

    private void sendMessage(WebSocketSession session) throws IOException {
        session.sendMessage(new TextMessage("ölkjsadflk"));
    }
}
