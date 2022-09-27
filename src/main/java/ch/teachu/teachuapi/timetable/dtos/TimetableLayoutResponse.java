package ch.teachu.teachuapi.timetable.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimetableLayoutResponse {
    private String timetableId;
    private Time start;
    private Time end;
}