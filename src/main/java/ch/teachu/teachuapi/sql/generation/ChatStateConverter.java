package ch.teachu.teachuapi.sql.generation;

import ch.teachu.teachuapi.enums.ChatState;
import org.jooq.Converter;

public class ChatStateConverter implements Converter<String, ChatState> {
    
    @Override
    public ChatState from(String databaseObject) {
        return ChatState.valueOf(databaseObject);
    }

    @Override
    public String to(ChatState userObject) {
        return userObject.name();
    }

    @Override
    public Class<String> fromType() {
        return String.class;
    }

    @Override
    public Class<ChatState> toType() {
        return ChatState.class;
    }
}
