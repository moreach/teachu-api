package ch.teachu.teachuapi.timetable.dtos;

import ch.teachu.teachuapi.parent.enums.Weekday;
import ch.teachu.teachuapi.user.dtos.ExternalUserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimetableResponse {
    private String className;
    private int lessonNumber;
    private String room;
    private Weekday weekday;
    private String subjectName;
    private ExternalUserResponse teacher;
    // todo event
}