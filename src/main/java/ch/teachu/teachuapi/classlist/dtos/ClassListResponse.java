package ch.teachu.teachuapi.classlist.dtos;

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
public class ClassListResponse {
    private String name;
    private ExternalUserResponse classTeacher;
    private List<ExternalUserResponse> students;
    private List<ExternalUserResponse> teachers;
}