package ch.teachu.teachuapi.absence;

import ch.teachu.teachuapi.absence.dtos.AbsenceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Absence")
@RequestMapping("/absence")
@RestController
public class AbsenceController {

    private final AbsenceService absenceService;

    public AbsenceController(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    // todo not finished waiting on db redesign
    @Operation(summary = "Load all absences (not working at all)")
    @GetMapping
    private ResponseEntity<List<AbsenceResponse>> getAbsences(@RequestHeader("access") String access) {
        return absenceService.getAbsences(access);
    }
}
