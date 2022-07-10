package ch.teachu.teachuapi.grade.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClassGradesResponse {
    private UUID schoolClassId;
    private String schoolClass;
    private String teacherFirstName;
    private String teacherLastName;
    private double averageMark;
    private List<SubjectGradesResponse> subjects;
}
