package ch.teachu.teachuapi.parent;

import ch.teachu.teachuapi.parent.dtos.ChildResponse;
import ch.teachu.teachuapi.parent.dtos.ParentDAO;
import ch.teachu.teachuapi.shared.AbstractService;
import ch.teachu.teachuapi.shared.dtos.SharedDAO;
import ch.teachu.teachuapi.shared.enums.UserRole;
import ch.teachu.teachuapi.sql.SQL;
import ch.teachu.teachuapi.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParentService extends AbstractService {

    private final UserService userService;

    public ParentService(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<List<ChildResponse>> getChildren(String access) {
        SharedDAO sharedDAO = authExactRole(access, UserRole.parent);

        List<ParentDAO> parentDAOs = new ArrayList<>();

        SQL.select("" +
                        "SELECT BIN_TO_UUID(ps.student_id) " +
                        "FROM   parent_student ps " +
                        "INNER JOIN user u on ps.student_id = u.id " +
                        "WHERE  ps.parent_id = UUID_TO_BIN(-userId) " +
                        "AND    u.active IS TRUE " +
                        "INTO   :userId ",
                parentDAOs,
                ParentDAO.class,
                sharedDAO);

        List<ChildResponse> childResponses = new ArrayList<>();

        for (ParentDAO parentDAO : parentDAOs) {
            childResponses.add(new ChildResponse(
                            parentDAO.getUserId(),
                            userService.getExternalUser(access, parentDAO.getUserId()).getBody()
                    )
            );
        }

        return ResponseEntity.ok(childResponses);
    }
}
