package ch.teachu.teachuapi.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SemesterExamsResponse {
    private UUID semesterId;
    private String semesterName;
    private List<SchoolClassExamsResponse> schoolClasses;
}
