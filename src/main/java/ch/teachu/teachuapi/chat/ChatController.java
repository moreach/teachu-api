package ch.teachu.teachuapi.chat;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Chat")
@RequestMapping("/chat")
@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * sockets
     * - chat messages
     * - receive
     * - send
     * - initial load (with all unused chat rooms)
     *
     * requests
     * - cud chat room (admin)
     * - leave char room (user)
     */

//    @Operation(summary = "Testing")
//    @PostMapping
//    private ResponseEntity<List<TimetableResponse>> createChat(@RequestHeader("access") String access) {
//        return ResponseEntity.ok(chatService.createChat);
//    }
}
