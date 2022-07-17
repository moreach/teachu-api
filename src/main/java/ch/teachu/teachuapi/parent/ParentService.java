package ch.teachu.teachuapi.parent;

import ch.teachu.teachuapi.parent.dtos.ChildDAO;
import ch.teachu.teachuapi.parent.dtos.ChildResponse;
import ch.teachu.teachuapi.shared.AbstractService;
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
        authExactRole(access, UserRole.parent);

        ChildDAO childParentAccessDAO = new ChildDAO();
        childParentAccessDAO.setParentAccess(access);

        List<ChildDAO> childDAOs = new ArrayList<>();

        SQL.select("" +
                        "SELECT BIN_TO_UUID(student_id) " +
                        "FROM   user u " +
                        "INNER JOIN parent_student ps ON u.id = ps.student_id " +
                        "INNER JOIN token t ON ps.parent_id = t.user_id " +
                        "WHERE  t.access = -parentAccess " +
                        "AND    u.active IS TRUE " +
                        "INTO   :userId",
                childDAOs,
                ChildDAO.class,
                childParentAccessDAO);

        List<ChildResponse> childResponses = new ArrayList<>();

        for (ChildDAO childDAO : childDAOs) {
            childResponses.add(new ChildResponse(
                            childDAO.getUserId(),
                            userService.getExternalUser(access, childDAO.getUserId()).getBody()
                    )
            );
        }


        return ResponseEntity.ok(childResponses);
    }
}
