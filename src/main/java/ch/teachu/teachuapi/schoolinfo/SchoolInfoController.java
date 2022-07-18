package ch.teachu.teachuapi.schoolinfo;

import ch.teachu.teachuapi.schoolinfo.dtos.SchoolInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "School info")
@RequestMapping("/schoolinfo")
@RestController
public class SchoolInfoController {

    private final SchoolInfoService schoolInfoService;

    public SchoolInfoController(SchoolInfoService schoolInfoService) {
        this.schoolInfoService = schoolInfoService;
    }

    @Operation(summary = "Load all school infos")
    @GetMapping
    private ResponseEntity<List<SchoolInfoResponse>> getSchoolInfos(@RequestHeader("access") String access) {
        return schoolInfoService.getSchoolInfos(access);
    }
}
