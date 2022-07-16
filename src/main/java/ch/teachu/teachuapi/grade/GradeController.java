package ch.teachu.teachuapi.grade;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Grade")
@RequestMapping("/grade")
@RestController
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

//    @Operation(summary = "Load grades")
//    @GetMapping("/{studentId}")
//    private ResponseEntity<SemestersGradesResponse> loadGrades(@RequestHeader("auth") String auth, @PathVariable UUID studentId) {
//        return gradeService.loadGrades(auth, studentId);
//    }
}
