package ch.teachu.teachuapi.parent;

import ch.teachu.teachuapi.parent.dtos.ChildResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Parent")
@RequestMapping("/parent")
@RestController
public class ParentController {

    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @Operation(summary = "Load children of parent")
    @GetMapping
    private ResponseEntity<List<ChildResponse>> getChildren(@RequestHeader("auth") String auth) {
        return parentService.getChildren(auth);
    }
}
