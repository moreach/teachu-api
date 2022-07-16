package ch.teachu.teachuapi.search;

import ch.teachu.teachuapi.parent.AbstractService;
import ch.teachu.teachuapi.parent.enums.UserRole;
import ch.teachu.teachuapi.search.dtos.SearchUserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService extends AbstractService {
    public ResponseEntity<List<SearchUserResponse>> searchUser(String access, String query) {
        authMinRole(access, UserRole.parent);

        List<SearchUserResponse> searchUserResponses = new ArrayList<>();

        searchUserResponses.add(new SearchUserResponse("mockUserId", "mockUsername"));
        searchUserResponses.add(new SearchUserResponse("mockUserId", "mockUsername"));
        searchUserResponses.add(new SearchUserResponse("mockUserId", "mockUsername"));
        searchUserResponses.add(new SearchUserResponse("mockUserId", "mockUsername"));

        return ResponseEntity.ok(searchUserResponses);
    }
}
