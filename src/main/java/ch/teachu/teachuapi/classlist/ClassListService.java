package ch.teachu.teachuapi.classlist;

import ch.teachu.teachuapi.classlist.dtos.ClassListDAO;
import ch.teachu.teachuapi.classlist.dtos.ClassListResponse;
import ch.teachu.teachuapi.shared.AbstractService;
import ch.teachu.teachuapi.shared.enums.UserRole;
import ch.teachu.teachuapi.sql.SQL;
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

        ClassListDAO classListAccessDAO = new ClassListDAO();
        classListAccessDAO.setAccess(access);

        List<ClassListDAO> classListDAOs = new ArrayList<>();

        SQL.select("" +
                        "SELECT BIN_TO_UUID(sc.id), " +
                        "       sc.name, " +
                        "       BIN_TO_UUID(sc.teacher_id) " +
                        "FROM   school_class sc " +
                        "INNER JOIN school_class_user scu ON sc.id = scu.school_class_id " +
                        "INNER JOIN user u ON scu.user_id = u.id " +
                        "INNER JOIN token t ON u.id = t.user_id " +
                        "WHERE  t.access = -access " +
                        "INTO   :id, " +
                        "       :name" +
                        "       :teacherId",
                classListDAOs,
                ClassListDAO.class,
                classListAccessDAO);

        List<ClassListResponse> classListResponses = new ArrayList<>();

        for (ClassListDAO classListDAO : classListDAOs) {
            List<ClassListDAO> classListStudentDAOs = new ArrayList<>();

            SQL.select("" +
                            "SELECT BIN_TO_UUID(u.id) " +
                            "FROM   school_class_user scu " +
                            "INNER JOIN user u ON scu.user_id = u.id " +
                            "WHERE  scu.school_class_id = UUID_TO_BIN(-id) " +
                            "INTO   :userId",
                    classListStudentDAOs,
                    ClassListDAO.class,
                    classListDAO);

            List<ExternalUserResponse> students = new ArrayList<>();

            for (ClassListDAO classListStudentDAO : classListStudentDAOs) {
                students.add(
                        userService.getExternalUser(access, classListStudentDAO.getUserId()).getBody()
                );
            }


            List<ClassListDAO> classListTeacherDAOs = new ArrayList<>();

            SQL.select("" +
                            "SELECT BIN_TO_UUID(teacher_id) " +
                            "FROM   school_class_subject " +
                            "WHERE  school_class_id = UUID_TO_BIN(-id) " +
                            "INTO   :userId",
                    classListTeacherDAOs,
                    ClassListDAO.class,
                    classListDAO);

            List<ExternalUserResponse> teachers = new ArrayList<>();

            for (ClassListDAO classListTeacherDAO : classListTeacherDAOs) {
                teachers.add(
                        userService.getExternalUser(access, classListTeacherDAO.getUserId()).getBody()
                );
            }

            classListResponses.add(new ClassListResponse(
                            classListDAO.getName(),
                            userService.getExternalUser(access, classListDAO.getTeacherId()).getBody(),
                            students,
                            teachers
                    )
            );
        }

        return ResponseEntity.ok(classListResponses);
    }
}
