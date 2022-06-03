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
public class StudentGradesDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private double averageMark;
    private List<ExamGradeDTO> grades;
}
