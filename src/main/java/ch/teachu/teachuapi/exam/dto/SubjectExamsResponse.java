package ch.teachu.teachuapi.exam.dto;

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
public class SubjectExamsResponse {
    private UUID subjectId;
    private String subjectName;
    private String teacherFirstName;
    private String teacherLastName;
    private double weight;
    private double averageMark;
    private List<ExamResponse> exams;
}
