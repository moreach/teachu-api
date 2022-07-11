package ch.teachu.teachuapi.grade.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeResponse {
    private String name;
    private String description;
    private double weight;
    private Date date;
    private Date viewDate;
    private double mark;
    private String note;
}
