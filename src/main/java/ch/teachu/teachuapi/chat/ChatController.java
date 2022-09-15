package ch.teachu.teachuapi.chat;

import ch.teachu.teachuapi.chat.dtos.ChatRequest;
import ch.teachu.teachuapi.chat.dtos.ChatResponse;
import ch.teachu.teachuapi.chat.dtos.MessageRequest;
import ch.teachu.teachuapi.chat.dtos.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Chat")
@RequestMapping("/chat")
@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @Operation(summary = "Get all chats")
    @GetMapping
    private ResponseEntity<List<ChatResponse>> getChats(@RequestHeader("access") String access) {
        return ResponseEntity.ok(chatService.getChats(access));
    }

    @Operation(summary = "Create chat")
    @PostMapping
    private ResponseEntity<ch.teachu.teachuapi.shared.dtos.MessageResponse> createChat(
            @RequestHeader("access") String access,
            @RequestBody ChatRequest chatRequest) {
        return ResponseEntity.ok(chatService.createChat(access, chatRequest));
    }

    @Operation(summary = "Update chat")
    @PutMapping("/{chatId}")
    private ResponseEntity<ch.teachu.teachuapi.shared.dtos.MessageResponse> updateChat(
            @RequestHeader("access") String access,
            @PathVariable String chatId,
            @RequestBody ChatRequest chatRequest) {
        return ResponseEntity.ok(chatService.updateChat(access, chatId, chatRequest));
    }

    @Operation(summary = "Get all messages")
    @GetMapping("/messages/{chatId}")
    private ResponseEntity<List<MessageResponse>> getMessages(
            @RequestHeader("access") String access,
            @PathVariable String chatId) {
        return ResponseEntity.ok(chatService.getMessages(access, chatId));
    }

    @Operation(summary = "Create message")
    @PostMapping("/messages")
    private ResponseEntity<ch.teachu.teachuapi.shared.dtos.MessageResponse> createMessage(
            @RequestHeader("access") String access,
            @RequestBody MessageRequest messageRequest) {
        return ResponseEntity.ok(chatService.createMessage(access, messageRequest));
    }
}
