package ch.teachu.teachuapi.grade;

import ch.teachu.teachuapi.grade.dtos.SemesterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Grade")
@RequestMapping("/grade")
@RestController
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @Operation(summary = "Load grades")
    @GetMapping()
    private ResponseEntity<List<SemesterResponse>> getGrades(@RequestHeader("access") String access, @RequestParam(required = false) String studentId) {
        return gradeService.getGrades(access, studentId);
    }
}
