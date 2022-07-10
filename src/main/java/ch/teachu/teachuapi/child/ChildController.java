package ch.teachu.teachuapi.child;

import ch.teachu.teachuapi.child.dto.OutlineChildrenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
