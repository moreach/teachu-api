package ch.teachu.teachuapi.child;

import ch.teachu.teachuapi.child.dto.ChildResponse;
import ch.teachu.teachuapi.child.dto.OutlineChildrenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Child")
@RequestMapping("/child")
@RestController
public class ChildController {

    private final ChildService childService;

    public ChildController(ChildService childService) {
        this.childService = childService;
    }

    @Operation(summary = "Load children of parent")
    @GetMapping
    private ResponseEntity<OutlineChildrenResponse> getChildren(@RequestHeader("auth") String auth) {
        return childService.getChildren(auth);
    }

    @Operation(summary = "Load child data as a parent")
    @GetMapping("/{studentId}")
    private ResponseEntity<ChildResponse> getChild(@RequestHeader("auth") String auth, @PathVariable("studentId") UUID studentId) {
        return childService.getChild(auth, studentId);
    }
}
