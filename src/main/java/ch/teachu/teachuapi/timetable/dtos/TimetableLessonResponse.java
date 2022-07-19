package ch.teachu.teachuapi.timetable.dtos;

import ch.teachu.teachuapi.user.dtos.ExternalUserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimetableLessonResponse {
    private String schoolClass;
    private String subject;
    private ExternalUserResponse teacher;
    private String timetableId;
    private String room;
    private TimetableLessonEventResponse event;
}