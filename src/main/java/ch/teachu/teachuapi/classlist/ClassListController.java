package ch.teachu.teachuapi.classlist;

import ch.teachu.teachuapi.classlist.dtos.ClassListResponse;
import ch.teachu.teachuapi.schoolinfo.dtos.SchoolInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Class list")
@RequestMapping("classlist")
@RestController
public class ClassListController {

    private final ClassListService classListService;

    public ClassListController(ClassListService classListService) {
        this.classListService = classListService;
    }

    @Operation(summary = "Load class list")
    @GetMapping
    private ResponseEntity<List<ClassListResponse>> getClassList(@RequestHeader("access") String access) {
        return classListService.getClassList(access);
    }
}
