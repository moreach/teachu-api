package ch.teachu.teachuapi.student.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentSchoolClassDTO {
    private String schoolClass;
    private String teacherFirstName;
    private String teacherLastName;
    private double averageMark;
    private List<StudentSubjectDTO> subjects;
}
