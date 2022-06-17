package ch.teachu.teachuapi.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamResponse {
    private UUID id;
    private String name;
    private String description;
    private double weight;
    private Date date;
    private Date viewDate;
    private double averageMark;
    private List<GradeResponse> grades;
}
