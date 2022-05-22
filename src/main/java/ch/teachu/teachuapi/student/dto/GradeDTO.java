package ch.teachu.teachuapi.student.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeDTO {
    private UUID id;
    private String name;
    private String description;
    private double weight;
    private Date date;
    private Date viewDate;
    private double mark;
    private String note;
}
