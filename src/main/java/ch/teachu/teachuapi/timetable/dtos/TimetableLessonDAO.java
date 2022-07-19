package ch.teachu.teachuapi.timetable.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimetableLessonDAO {
    private String id;
    private String schoolClass;
    private String subject;
    private String teacherId;
    private String timetableId;
    private String room;
}