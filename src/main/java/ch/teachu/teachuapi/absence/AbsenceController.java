package ch.teachu.teachuapi.absence;

import ch.teachu.teachuapi.absence.dtos.AbsenceRequest;
import ch.teachu.teachuapi.absence.dtos.AbsenceResponse;
import ch.teachu.teachuapi.shared.dtos.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Absence")
@RequestMapping("/absence")
@RestController
public class AbsenceController {

    private final AbsenceService absenceService;

    public AbsenceController(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    @Operation(summary = "Load all absences")
    @GetMapping
    private ResponseEntity<List<AbsenceResponse>> getAbsences(@RequestHeader("access") String access, @RequestParam(required = false) String studentId) {
        return ResponseEntity.ok(absenceService.getAbsences(access, studentId));
    }

    @Operation(summary = "create absence")
    @PostMapping
    private ResponseEntity<MessageResponse> createAbsence(
            @RequestHeader("access") String access,
            @RequestParam(required = false) String studentId,
            @RequestBody AbsenceRequest absenceRequest) {
        return ResponseEntity.ok(absenceService.createAbsence(access, studentId, absenceRequest));
    }

    @Operation(summary = "update absence")
    @PutMapping("/{absenceId}")
    private ResponseEntity<MessageResponse> updateAbsence(
            @RequestHeader("access") String access,
            @RequestParam(required = false) String studentId,
            @PathVariable("absenceId") String absenceId,
            @RequestBody AbsenceRequest absenceRequest) {
        return ResponseEntity.ok(absenceService.updateAbsence(access, studentId, absenceId, absenceRequest));
    }
}
