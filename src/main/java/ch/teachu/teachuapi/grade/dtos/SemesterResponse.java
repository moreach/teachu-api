package ch.teachu.teachuapi.grade.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SemesterResponse {
    private String id;
    private String name;
    private Date from;
    private Date to;
    private List<ClassResponse> classes;
}