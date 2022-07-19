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
public class TimetableEventDAO {
    private Date from;
    private Date to;
    private String title;
    private String description;
    private String type;
    private String state;
}