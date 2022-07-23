package ch.teachu.teachuapi.search;

import ch.teachu.teachuapi.search.dtos.SearchUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Search")
@RequestMapping("/search")
@RestController
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @Operation(summary = "Search users")
    @GetMapping("/user")
    private ResponseEntity<List<SearchUserResponse>> lookupStudent(@RequestHeader("access") String access, @RequestParam String query) {
        return ResponseEntity.ok(searchService.searchUser(access, query));
    }
}
