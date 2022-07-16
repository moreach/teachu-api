//package ch.teachu.old.sql.generation;
//
//import ch.teachu.old.enums.ChatState;
//import org.jooq.Converter;
//
//public class ChatStateConverter implements Converter<String, ChatState> {
//
//    @Override
//    public ChatState from(String databaseObject) {
//        return ChatState.valueOf(databaseObject.toUpperCase());
//    }
//
//    @Override
//    public String to(ChatState userObject) {
//        return userObject.name().toLowerCase();
//    }
//
//    @Override
//    public Class<String> fromType() {
//        return String.class;
//    }
//
//    @Override
//    public Class<ChatState> toType() {
//        return ChatState.class;
//    }
//}
