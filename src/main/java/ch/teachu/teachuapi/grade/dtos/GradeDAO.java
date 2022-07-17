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
public class GradeDAO {
    private String semesterId;
    private String semesterName;
    private Date semesterFrom;
    private Date semesterTo;
    private String classId;
    private String className;
    private String classTeacherId;
    private String subjectId;
    private String subjectName;
    private Double subjectWeight;
    private String subjectTeacherId;
    private String examName;
    private String examDescription;
    private Double examWeight;
    private Date examDate;
    private Double examMark;
}