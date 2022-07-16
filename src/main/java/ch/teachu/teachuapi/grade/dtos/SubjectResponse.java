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
public class SubjectResponse {
    private String id;
    private String name;
    private ExternalUserResponse teacher;
    private double weight;
    private double average;
    private List<GradeResponse> grades;
}