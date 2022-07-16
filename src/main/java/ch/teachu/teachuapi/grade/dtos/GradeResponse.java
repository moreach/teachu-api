package ch.teachu.teachuapi.grade.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GradeResponse {
    private String name;
    private String description;
    private Date date;
    private double weight;
    private double mark;
}