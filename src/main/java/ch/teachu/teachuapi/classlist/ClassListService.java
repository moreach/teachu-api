package ch.teachu.teachuapi.classlist;

import ch.teachu.teachuapi.classlist.dtos.ClassListDAO;
import ch.teachu.teachuapi.classlist.dtos.ClassListResponse;
import ch.teachu.teachuapi.shared.AbstractService;
import ch.teachu.teachuapi.shared.dtos.SharedDAO;
import ch.teachu.teachuapi.sql.SQL;
import ch.teachu.teachuapi.user.UserService;
import ch.teachu.teachuapi.user.dtos.ExternalUserResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ClassListService extends AbstractService {

    private final UserService userService;

    public ClassListService(UserService userService) {
        this.userService = userService;
    }

    public List<ClassListResponse> getClassList(String access, String studentId) {
        SharedDAO sharedDAO = authStudentId(access, studentId);

        List<ClassListDAO> classListDAOs = new ArrayList<>();

        SQL.select("" +
                        "SELECT BIN_TO_UUID(sc.id), " +
                        "       sc.name, " +
                        "       BIN_TO_UUID(sc.teacher_id) " +
                        "FROM   school_class sc " +
                        "INNER JOIN school_class_user scu ON sc.id = scu.school_class_id " +
                        "WHERE  scu.user_id = UUID_TO_BIN(-userId) " +
                        "INTO   :id, " +
                        "       :name" +
                        "       :teacherId",
                classListDAOs,
                ClassListDAO.class,
                sharedDAO);

        List<ClassListResponse> classListResponses = new ArrayList<>();

        for (ClassListDAO classListDAO : classListDAOs) {
            List<ClassListDAO> classListStudentDAOs = new ArrayList<>();

            SQL.select("" +
                            "SELECT BIN_TO_UUID(u.id) " +
                            "FROM   school_class_user scu " +
                            "INNER JOIN user u ON scu.user_id = u.id " +
                            "WHERE  scu.school_class_id = UUID_TO_BIN(-id) " +
                            "AND    u.active IS TRUE " +
                            "INTO   :userId",
                    classListStudentDAOs,
                    ClassListDAO.class,
                    classListDAO);

            List<ExternalUserResponse> students = new ArrayList<>();

            for (ClassListDAO classListStudentDAO : classListStudentDAOs) {
                students.add(
                        userService.getExternalUser(access, classListStudentDAO.getUserId())
                );
            }

            List<ClassListDAO> classListTeacherDAOs = new ArrayList<>();

            SQL.select("" +
                            "SELECT BIN_TO_UUID(teacher_id) " +
                            "FROM   school_class_subject scs " +
                            "INNER JOIN user u ON scs.teacher_id = u.id " +
                            "WHERE  school_class_id = UUID_TO_BIN(-id) " +
                            "AND    u.active IS TRUE" +
                            "INTO   :userId",
                    classListTeacherDAOs,
                    ClassListDAO.class,
                    classListDAO);

            Set<ExternalUserResponse> teachers = new HashSet<>();

            for (ClassListDAO classListTeacherDAO : classListTeacherDAOs) {
                teachers.add(
                        userService.getExternalUser(access, classListTeacherDAO.getUserId())
                );
            }

            classListResponses.add(new ClassListResponse(
                            classListDAO.getName(),
                            userService.getExternalUser(access, classListDAO.getTeacherId()),
                            students,
                            new ArrayList<>(teachers)
                    )
            );
        }

        return classListResponses;
    }
}
