package ch.teachu.teachuapi.timetable.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimetableDAO {
    private String userId;
    private Date date;
    private String weekday;
    private String lessonId;
}