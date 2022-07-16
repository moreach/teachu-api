package ch.teachu.teachuapi.grade;

import ch.teachu.teachuapi.grade.dtos.*;
import ch.teachu.teachuapi.shared.AbstractService;
import ch.teachu.teachuapi.shared.enums.UserRole;
import ch.teachu.teachuapi.shared.errorhandlig.NotFoundException;
import ch.teachu.teachuapi.sql.SQL;
import ch.teachu.teachuapi.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GradeService extends AbstractService {

    private final UserService userService;

    public GradeService(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<List<SemesterResponse>> getGrades(String access, String studentId) {
        authMinRole(access, UserRole.parent);

        StudentDAO studentDAO = new StudentDAO();
        studentDAO.setAccess(access);
        studentDAO.setInputStudentId(studentId);

        if (studentDAO.getInputStudentId() == null) {
            SQL.select("" +
                            "SELECT BIN_TO_UUID(u.id) " +
                            "FROM   user u " +
                            "INNER JOIN token t on u.id = t.user_id " +
                            "WHERE  access = -access " +
                            "AND    u.role = 'student' " +
                            "AND    u.active IS TRUE " +
                            "INTO   :studentId",
                    studentDAO,
                    studentDAO);

            if (studentDAO.getStudentId() == null) {
                throw new NotFoundException("Student with this authentication");
            }
        } else {
            SQL.select("" +
                            "SELECT BIN_TO_UUID(ps.student_id) " +
                            "FROM   user u " +
                            "INNER JOIN parent_student ps on u.id = ps.parent_id " +
                            "INNER JOIN token t on u.id = t.user_id " +
                            "WHERE  t.access = -access " +
                            "AND    u.role = 'parent' " +
                            "AND    ps.student_id = UUID_TO_BIN(-inputStudentId) " +
                            "AND    u.active IS TRUE " +
                            "INTO   :studentId ",
                    studentDAO,
                    studentDAO
            );

            if (studentDAO.getStudentId() == null) {
                throw new NotFoundException("Student with this authentication (parent)");
            }
        }

        List<GradeDAO> gradeDAOs = new ArrayList<>();

        SQL.select("" +
                        "SELECT BIN_TO_UUID(se.id), " +
                        "       se.name, " +
                        "       se.date_from, " +
                        "       se.date_to, " +
                        "       BIN_TO_UUID(sc.id), " +
                        "       sc.name, " +
                        "       BIN_TO_UUID(sc.teacher_id), " +
                        "       BIN_TO_UUID(scs.id), " +
                        "       su.name, " +
                        "       su.weight, " +
                        "       BIN_TO_UUID(scs.teacher_id), " +
                        "       e.name, " +
                        "       e.description, " +
                        "       e.weight, " +
                        "       e.date, " +
                        "       g.mark " +
                        "FROM   grade g " +
                        "INNER JOIN exam e ON g.exam_id = e.id " +
                        "INNER JOIN school_class_subject scs ON e.school_class_subject_id = scs.id " +
                        "INNER JOIN subject su ON scs.subject_id = su.id " +
                        "INNER JOIN school_class sc ON scs.school_class_id = sc.id " +
                        "INNER JOIN school_class_semester scse ON sc.id = scse.school_class_id " +
                        "INNER JOIN semester se ON scse.semester_id = se.id " +
                        "WHERE  g.student_id = UUID_TO_BIN(-studentId) " +
                        "AND    e.view_date < NOW() " +
                        "INTO   :semesterId, " +
                        "       :semesterName, " +
                        "       :semesterFrom, " +
                        "       :semesterTo, " +
                        "       :classId, " +
                        "       :className, " +
                        "       :classTeacherId, " +
                        "       :subjectId, " +
                        "       :subjectName" +
                        "       :subjectWeight, " +
                        "       :subjectTeacherId, " +
                        "       :examName, " +
                        "       :examDescription, " +
                        "       :examWeight, " +
                        "       :examDate, " +
                        "       :examMark ",
                gradeDAOs,
                GradeDAO.class,
                studentDAO);

        List<SemesterResponse> semesterResponses = new ArrayList<>();

        for (GradeDAO gradeDAO : gradeDAOs) {

            SemesterResponse semesterResponse = findSemester(semesterResponses, gradeDAO);

            if (semesterResponse == null) {
                semesterResponse = new SemesterResponse(
                        gradeDAO.getSemesterId(),
                        gradeDAO.getSemesterName(),
                        gradeDAO.getSemesterFrom(),
                        gradeDAO.getSemesterTo(),
                        new ArrayList<>()
                );

                semesterResponses.add(semesterResponse);
            }

            ClassResponse classResponse = findClass(semesterResponse.getClasses(), gradeDAO);

            if (classResponse == null) {
                classResponse = new ClassResponse(
                        gradeDAO.getClassId(),
                        gradeDAO.getClassName(),
                        userService.getExternalUser(access, gradeDAO.getClassTeacherId()).getBody(),
                        0.0,
                        new ArrayList<>()
                );

                semesterResponse.getClasses().add(classResponse);
            }

            SubjectResponse subjectResponse = findSubject(classResponse.getSubjects(), gradeDAO);

            if (subjectResponse == null) {
                subjectResponse = new SubjectResponse(
                        gradeDAO.getSubjectId(),
                        gradeDAO.getSubjectName(),
                        userService.getExternalUser(access, gradeDAO.getSubjectTeacherId()).getBody(),
                        gradeDAO.getSubjectWeight(),
                        0.0,
                        new ArrayList<>()
                );

                classResponse.getSubjects().add(subjectResponse);
            }

            GradeResponse gradeResponse = new GradeResponse(
                    gradeDAO.getExamName(),
                    gradeDAO.getExamDescription(),
                    gradeDAO.getExamDate(),
                    gradeDAO.getExamWeight(),
                    gradeDAO.getExamMark()
            );

            subjectResponse.getGrades().add(gradeResponse);
        }

        for (SemesterResponse semesterResponse : semesterResponses) {
            for (ClassResponse classResponse : semesterResponse.getClasses()) {
                calculateAverages(classResponse);
            }

        }

        return ResponseEntity.ok(semesterResponses);
    }

    private SemesterResponse findSemester(List<SemesterResponse> semesterResponses, GradeDAO gradeDAO) {
        for (SemesterResponse semesterResponse : semesterResponses) {
            if (Objects.equals(semesterResponse.getId(), gradeDAO.getSemesterId())) {
                return semesterResponse;
            }
        }

        return null;
    }

    private ClassResponse findClass(List<ClassResponse> classResponses, GradeDAO gradeDAO) {
        for (ClassResponse classResponse : classResponses) {
            if (Objects.equals(classResponse.getId(), gradeDAO.getClassId())) {
                return classResponse;
            }
        }

        return null;
    }

    private SubjectResponse findSubject(List<SubjectResponse> subjectResponses, GradeDAO gradeDAO) {
        for (SubjectResponse subjectResponse : subjectResponses) {
            if (Objects.equals(subjectResponse.getId(), gradeDAO.getSubjectId())) {
                return subjectResponse;
            }
        }

        return null;
    }

    private void calculateAverages(ClassResponse classResponse) {
        double sum = 0;
        double weight = 0;
        for (SubjectResponse subjectResponse : classResponse.getSubjects()) {
            calculateAverages(subjectResponse);
            sum += subjectResponse.getAverage() * subjectResponse.getWeight();
            weight += subjectResponse.getWeight();
        }
        classResponse.setAverage(round2DecimalPlaces(sum / weight));
    }

    private void calculateAverages(SubjectResponse subjectResponse) {
        double sum = 0;
        double weight = 0;
        for (GradeResponse gradeResponse : subjectResponse.getGrades()) {
            sum += gradeResponse.getMark() * gradeResponse.getWeight();
            weight += gradeResponse.getWeight();
        }
        subjectResponse.setAverage(round2DecimalPlaces(sum / weight));
    }

    private double round2DecimalPlaces(double d) {
        return Math.round(d * 100.0) / 100.0;
    }
}
