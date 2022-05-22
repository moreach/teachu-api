package ch.teachu.teachuapi.student;

import ch.teachu.teachuapi.student.dto.StudentSemestersReponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Student")
@RequestMapping("/student")
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(summary = "Load marks")
    @GetMapping("/mark")
    private ResponseEntity<StudentSemestersReponse> loadMarks(@RequestHeader("auth") String auth) {
        return studentService.loadMarks(auth);
    }
}
