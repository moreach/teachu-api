package ch.teachu.teachuapi.timetable.dtos;

import ch.teachu.teachuapi.shared.enums.Weekday;
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
public class TimetableResponse {
    private Date date;
    private Weekday weekday;
    private TimetableUserResponse userEvent;
    private TimetableSchoolClassResponse SchoolClassEvent;
    private TimetableSchoolResponse schoolEvent;
    private List<TimetableLessonResponse> lessons;
}