package ch.teachu.teachuapi.timetable;

import ch.teachu.teachuapi.timetable.dtos.TimetableLayoutResponse;
import ch.teachu.teachuapi.timetable.dtos.TimetableRequest;
import ch.teachu.teachuapi.timetable.dtos.TimetableResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Timetable")
@RequestMapping("/timetable")
@RestController
public class TimetableController {

    private final TimetableService timetableService;

    public TimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @Operation(summary = "Load timetable in defined timeframe (fails in swagger since js restricts body in get requests)")
    @GetMapping
    private ResponseEntity<List<TimetableResponse>> getTimetable(
            @RequestHeader("access") String access,
            @RequestParam(required = false) String studentId,
            @RequestBody TimetableRequest timetableRequest) {
        return timetableService.getTimetable(access, studentId, timetableRequest);
    }

    @Operation(summary = "Get layout of the timetable")
    @GetMapping("/layout")
    private ResponseEntity<List<TimetableLayoutResponse>> getTimetableLayout(@RequestHeader("access") String access) {
        return timetableService.getTimetableLayout(access);
    }
}
