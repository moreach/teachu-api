package ch.teachu.teachuapi.grade;

import ch.teachu.teachuapi.dtos.MessageResponse;
import ch.teachu.teachuapi.grade.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Grade")
@RequestMapping("/grade")
@RestController
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @Operation(summary = "Load grades with restriction")
    @GetMapping("/restriction")
    private ResponseEntity<GradesResponse> loadGradesWithRestriction(@RequestHeader("auth") String auth, LoadGradeRequest request) {
        return gradeService.loadWithRestriction(auth, request);
    }

    @Operation(summary = "Create grade")
    @PostMapping("/{studentId}")
    private ResponseEntity<CreateGradeResponse> createGrade(@RequestHeader("auth") String auth, CreateGradeRequest request, @PathVariable UUID studentId) {
        return gradeService.createGrade(auth, request, studentId);
    }

    @Operation(summary = "Change grade")
    @PutMapping("/{studentId}")
    private ResponseEntity<MessageResponse> changeGrade(@RequestHeader("auth") String auth, ChangeGradeRequest request, @PathVariable UUID studentId) {
        return gradeService.changeGrade(auth, request, studentId);
    }

    @Operation(summary = "Delete grade")
    @DeleteMapping("/{gradeId}")
    private ResponseEntity<MessageResponse> deleteGrade(@RequestHeader("auth") String auth, @PathVariable UUID gradeId) {
        return gradeService.deleteGrade(auth, gradeId);
    }
}
