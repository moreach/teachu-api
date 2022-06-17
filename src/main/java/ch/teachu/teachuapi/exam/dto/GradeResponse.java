package ch.teachu.teachuapi.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeResponse {
    private UUID id;
    private UUID studentId;
    private double mark;
    private String note;
    private String studentFirstName;
    private String studentLastName;
}
