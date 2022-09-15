package ch.teachu.teachuapi.chat.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequest {
    private String title;
    private String description;
    private List<String> memberIds;
}