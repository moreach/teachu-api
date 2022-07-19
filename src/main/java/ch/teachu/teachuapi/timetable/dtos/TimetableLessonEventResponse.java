package ch.teachu.teachuapi.timetable.dtos;

import ch.teachu.teachuapi.shared.enums.LessonEventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimetableLessonEventResponse {
    private String title;
    private String description;
    private LessonEventType type;
}