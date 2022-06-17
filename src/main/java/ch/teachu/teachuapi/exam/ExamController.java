package ch.teachu.teachuapi.exam;

import ch.teachu.teachuapi.dtos.MessageResponse;
import ch.teachu.teachuapi.exam.dto.ChangeExamRequest;
import ch.teachu.teachuapi.exam.dto.CreateExamRequest;
import ch.teachu.teachuapi.exam.dto.SemestersExamsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Exam")
@RequestMapping("/exam")
@RestController
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @Operation(summary = "Load exams")
    @GetMapping
    private ResponseEntity<SemestersExamsResponse> loadExams(@RequestHeader("auth") String auth) {
        return examService.loadExams(auth);
    }

    @Operation(summary = "Create exam")
    @PostMapping
    private ResponseEntity<MessageResponse> createExam(@RequestHeader("auth") String auth, @RequestBody CreateExamRequest createExamRequest) {
        return examService.createExam(auth, createExamRequest);
    }

    @Operation(summary = "Change exam")
    @PutMapping
    private ResponseEntity<MessageResponse> changeExam(@RequestHeader("auth") String auth, @RequestBody ChangeExamRequest changeExamRequest) {
        return examService.changeExam(auth, changeExamRequest);
    }

    @Operation(summary = "Delete exam")
    @DeleteMapping("/{examId}")
    private ResponseEntity<MessageResponse> deleteExam(@RequestHeader("auth") String auth, @PathVariable UUID examId) {
        return examService.deleteExam(auth, examId);
    }
}
