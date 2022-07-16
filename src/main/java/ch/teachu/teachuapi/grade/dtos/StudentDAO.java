package ch.teachu.teachuapi.grade.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDAO {
    private String access;
    private String inputStudentId;
    private String studentId;
}