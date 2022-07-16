package ch.teachu.teachuapi.parent;

import ch.teachu.teachuapi.parent.dtos.ChildResponse;
import ch.teachu.teachuapi.shared.AbstractService;
import ch.teachu.teachuapi.shared.enums.UserRole;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParentService extends AbstractService {
    public ResponseEntity<List<ChildResponse>> getChildren(String auth) {
        authExactRole(auth, UserRole.parent);

        ChildResponse childResponse = new ChildResponse(
                "mockId",
                "mockFirstName"
        );

        List<ChildResponse> childResponses = new ArrayList<>();
        childResponses.add(childResponse);
        childResponses.add(childResponse);

        return ResponseEntity.ok(childResponses);
    }
}
