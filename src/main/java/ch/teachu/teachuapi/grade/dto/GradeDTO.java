package ch.teachu.teachuapi.grade.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeDTO {
    private UUID id;
    private String studentFirstName;
    private String studentLastName;
    private double mark;
    private String note;
}
