package ch.teachu.teachuapi.timetable.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimetableLayoutResponse {
    private int lessonNumber;
    private String start;
    private String end;
}