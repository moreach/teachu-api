package ch.teachu.teachuapi.grade.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectGradesDTO {
    private String subjectName;
    private String teacherFirstName;
    private String teacherLastName;
    private double weight;
    private double averageMark;
    private List<ExamGradeDTO> grades;
}
