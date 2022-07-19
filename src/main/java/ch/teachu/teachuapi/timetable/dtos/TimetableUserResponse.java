package ch.teachu.teachuapi.timetable.dtos;

import ch.teachu.teachuapi.shared.enums.UserEventState;
import ch.teachu.teachuapi.shared.enums.UserEventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimetableUserResponse {
    private Date from;
    private Date to;
    private String title;
    private String description;
    private UserEventType type;
    private UserEventState state;
}