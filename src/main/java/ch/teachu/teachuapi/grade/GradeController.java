package ch.teachu.teachuapi.grade;

import ch.teachu.teachuapi.dtos.MessageResponse;
import ch.teachu.teachuapi.grade.dto.ChangeGradeRequest;
import ch.teachu.teachuapi.grade.dto.CreateGradeRequest;
import ch.teachu.teachuapi.grade.dto.GradesResponse;
import ch.teachu.teachuapi.grade.dto.SemestersGradesResponse;
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

    @Operation(summary = "Load grades. Can be called by student and teacher. The response will be sorted.")
    @GetMapping
    private ResponseEntity<SemestersGradesResponse> loadGrades(@RequestHeader("auth") String auth) {
        return gradeService.loadGrades(auth);
    }

    @Operation(summary = "Load grades by exam")
    @GetMapping("/exam/{examId}")
    private ResponseEntity<GradesResponse> loadGradesByExam(@RequestHeader("auth") String auth, @PathVariable UUID examId) {
        return gradeService.loadGrades(auth, examId);
    }

    @Operation(summary = "Create grade")
    @PostMapping
    private ResponseEntity<MessageResponse> createGrade(@RequestHeader("auth") String auth, CreateGradeRequest request) {
        return gradeService.createGrade(auth, request);
    }

    @Operation(summary = "Change grade")
    @PutMapping
    private ResponseEntity<MessageResponse> changeGrade(@RequestHeader("auth") String auth, ChangeGradeRequest request) {
        return gradeService.changeGrade(auth, request);
    }

    @Operation(summary = "Delete grade")
    @DeleteMapping("/{gradeId}")
    private ResponseEntity<MessageResponse> deleteGrade(@RequestHeader("auth") String auth, @PathVariable UUID gradeId) {
        return gradeService.deleteGrade(auth, gradeId);
    }
}
