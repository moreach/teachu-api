package ch.teachu.teachuapi.exam.dto;

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
public class CreateExamRequest {
    private String name;
    private String description;
    private double weight;
    private Date date;
    private Date viewDate;
    private UUID schoolClassId;
    private UUID subjectId;
    private UUID semesterId;
}
