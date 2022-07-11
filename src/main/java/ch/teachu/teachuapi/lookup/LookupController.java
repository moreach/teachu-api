package ch.teachu.teachuapi.lookup;

import ch.teachu.teachuapi.lookup.dto.LookupRequest;
import ch.teachu.teachuapi.lookup.dto.LookupResponse;
import ch.teachu.teachuapi.lookup.dto.LookupStudentRequest;
import ch.teachu.teachuapi.lookup.dto.SubjectLookupRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Lookup")
@RequestMapping("/lookup")
@RestController
public class LookupController {

    private final LookupService lookupService;

    public LookupController(LookupService lookupService) {
        this.lookupService = lookupService;
    }

    @Operation(summary = "Lookup semesters")
    @GetMapping("/semester")
    private ResponseEntity<LookupResponse> lookupSemesters(@RequestHeader("auth") String auth, LookupRequest lookupRequest) {
        return lookupService.lookupSemesters(auth, lookupRequest);
    }

    @Operation(summary = "Lookup subjects")
    @GetMapping("/subject")
    private ResponseEntity<LookupResponse> lookupSubjects(@RequestHeader("auth") String auth, SubjectLookupRequest lookupRequest) {
        return lookupService.lookupSubjects(auth, lookupRequest);
    }
    
    @Operation(summary = "Lookup students")
    @GetMapping("student")
    private ResponseEntity<LookupResponse> lookupStudent(@RequestHeader("auth") String auth, LookupStudentRequest request) {
        return lookupService.lookupStudent(auth, request);
    }
}
