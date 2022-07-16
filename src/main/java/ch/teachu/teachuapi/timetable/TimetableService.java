package ch.teachu.teachuapi.timetable;

import ch.teachu.teachuapi.shared.AbstractService;
import ch.teachu.teachuapi.shared.enums.UserRole;
import ch.teachu.teachuapi.shared.enums.Weekday;
import ch.teachu.teachuapi.timetable.dtos.TimetableLayoutResponse;
import ch.teachu.teachuapi.timetable.dtos.TimetableRequest;
import ch.teachu.teachuapi.timetable.dtos.TimetableResponse;
import ch.teachu.teachuapi.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimetableService extends AbstractService {

    private final UserService userService;

    public TimetableService(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<List<TimetableResponse>> getTimetable(String access, TimetableRequest timetableRequest) {
        authMinRole(access, UserRole.parent);

        // todo get date in defined timeframe
        // todo get teacherid
        // todo rework events

        TimetableResponse timetableResponse1 = new TimetableResponse(
                "mockClassName",
                1,
                "mockRoom",
                Weekday.monday,
                "mockSubject",
                userService.getExternalUser(access, "mockUserId").getBody()
        );

        TimetableResponse timetableResponse2 = new TimetableResponse(
                "mockClassName",
                2,
                "mockRoom",
                Weekday.tuesday,
                "mockSubject",
                userService.getExternalUser(access, "mockUserId").getBody()
        );

        TimetableResponse timetableResponse3 = new TimetableResponse(
                "mockClassName",
                3,
                "mockRoom",
                Weekday.wednesday,
                "mockSubject",
                userService.getExternalUser(access, "mockUserId").getBody()
        );

        List<TimetableResponse> timetableResponses = new ArrayList<>();
        timetableResponses.add(timetableResponse1);
        timetableResponses.add(timetableResponse2);
        timetableResponses.add(timetableResponse3);

        return ResponseEntity.ok(timetableResponses);
    }

    public ResponseEntity<List<TimetableLayoutResponse>> getTimetableLayout(String access) {
        authMinRole(access, UserRole.parent);

        // todo format that shit

        List<TimetableLayoutResponse> timetableLayoutResponses = new ArrayList<>();
        timetableLayoutResponses.add(new TimetableLayoutResponse(1, "08:15", "09:00"));
        timetableLayoutResponses.add(new TimetableLayoutResponse(2, "09:05", "09:50"));
        timetableLayoutResponses.add(new TimetableLayoutResponse(3, "10:10", "10:55"));
        timetableLayoutResponses.add(new TimetableLayoutResponse(4, "11:00", "11:45"));
        timetableLayoutResponses.add(new TimetableLayoutResponse(5, "13:00", "13:45"));
        timetableLayoutResponses.add(new TimetableLayoutResponse(6, "13:50", "14:35"));

        return ResponseEntity.ok(timetableLayoutResponses);
    }
}
