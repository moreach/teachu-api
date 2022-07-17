package ch.teachu.teachuapi.search;

import ch.teachu.teachuapi.search.dtos.SearchDAO;
import ch.teachu.teachuapi.search.dtos.SearchUserResponse;
import ch.teachu.teachuapi.shared.AbstractService;
import ch.teachu.teachuapi.shared.enums.UserRole;
import ch.teachu.teachuapi.sql.SQL;
import ch.teachu.teachuapi.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService extends AbstractService {

    private final UserService userService;

    public SearchService(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<List<SearchUserResponse>> searchUser(String access, String query) {
        authMinRole(access, UserRole.parent);

        SearchDAO searchQueryDAO = new SearchDAO();
        searchQueryDAO.setQuery(query);

        List<SearchDAO> searchDAOs = new ArrayList<>();

        SQL.select("" +
                        "SELECT BIN_TO_UUID(id) " +
                        "FROM   user " +
                        "WHERE  UPPER(first_name) LIKE CONCAT('%', UPPER(-query), '%') " +
                        "OR     UPPER(last_name) LIKE CONCAT('%', UPPER(-query), '%') " +
                        "INTO   :userId ",
                searchDAOs,
                SearchDAO.class,
                searchQueryDAO);

        List<SearchUserResponse> searchUserResponses = new ArrayList<>();

        for (SearchDAO searchDAO : searchDAOs) {
            searchUserResponses.add(new SearchUserResponse(
                            userService.getExternalUser(access, searchDAO.getUserId()).getBody()
                    )
            );
        }

        return ResponseEntity.ok(searchUserResponses);
    }
}
