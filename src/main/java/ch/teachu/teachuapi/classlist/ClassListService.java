package ch.teachu.teachuapi.classlist;

import ch.teachu.teachuapi.classlist.dtos.ClassListResponse;
import ch.teachu.teachuapi.parent.AbstractService;
import ch.teachu.teachuapi.parent.enums.UserRole;
import ch.teachu.teachuapi.user.UserService;
import ch.teachu.teachuapi.user.dtos.ExternalUserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassListService extends AbstractService {

    private final UserService userService;

    public ClassListService(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<List<ClassListResponse>> getClassList(String access) {
        authMinRole(access, UserRole.parent);

        // todo get all students + teachers from user (check active)

        List<ExternalUserResponse> students = new ArrayList<>();
        students.add(userService.getExternalUser(access, "mockUserId").getBody());
        students.add(userService.getExternalUser(access, "mockUserId").getBody());
        students.add(userService.getExternalUser(access, "mockUserId").getBody());

        List<ExternalUserResponse> teachers = new ArrayList<>();
        teachers.add(userService.getExternalUser(access, "mockUserId").getBody());
        teachers.add(userService.getExternalUser(access, "mockUserId").getBody());
        teachers.add(userService.getExternalUser(access, "mockUserId").getBody());

        ClassListResponse classListResponse = new ClassListResponse(
                "MockName",
                userService.getExternalUser(access, "mockUserId").getBody(),
                students,
                teachers
        );

        List<ClassListResponse> classListResponses = new ArrayList<>();
        classListResponses.add(classListResponse);
        classListResponses.add(classListResponse);

        return ResponseEntity.ok(classListResponses);
    }
}
