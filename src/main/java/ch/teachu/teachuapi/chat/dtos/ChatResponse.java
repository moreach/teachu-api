package ch.teachu.teachuapi.chat.dtos;

import ch.teachu.teachuapi.user.dtos.ExternalUserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {

    private String id;
    private String title;
    private String description;
    private ExternalUserResponse creator;
    private List<ExternalUserResponse> members;
}
