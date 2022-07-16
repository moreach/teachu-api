package ch.teachu.teachuapi.grade.dtos;

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
public class ClassResponse {
    private String id;
    private String name;
    private ExternalUserResponse classTeacher;
    private double average;
    private List<SubjectResponse> subjects;
}