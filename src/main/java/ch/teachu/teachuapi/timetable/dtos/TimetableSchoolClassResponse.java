package ch.teachu.teachuapi.timetable.dtos;

import ch.teachu.teachuapi.shared.enums.SchoolClassEventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimetableSchoolClassResponse {
    private Date from;
    private Date to;
    private String title;
    private String description;
    private SchoolClassEventType type;
}