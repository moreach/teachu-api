package ch.teachu.teachuapi.timetable;

import ch.teachu.teachuapi.shared.AbstractService;
import ch.teachu.teachuapi.shared.dtos.SharedDAO;
import ch.teachu.teachuapi.shared.enums.*;
import ch.teachu.teachuapi.sql.SQL;
import ch.teachu.teachuapi.timetable.dtos.*;
import ch.teachu.teachuapi.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class TimetableService extends AbstractService {

    private final UserService userService;

    public TimetableService(UserService userService) {
        this.userService = userService;
    }

    public List<TimetableResponse> getTimetable(String access, String studentId, TimetableRequest timetableRequest) {
        SharedDAO sharedDAO = authStudentId(access, studentId);

        LocalDate from = timetableRequest.getFrom()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate to = timetableRequest.getTo()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .plusDays(1);

        List<TimetableResponse> timetableResponses = new ArrayList<>();

        for (LocalDate localDate : from.datesUntil(to).collect(Collectors.toList())) {

            TimetableDAO timetableDAO = new TimetableDAO();

            timetableDAO.setUserId(sharedDAO.getUserId());
            timetableDAO.setDate(Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            timetableDAO.setWeekday(localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toLowerCase());

            TimetableEventDAO timetableEventDAO = new TimetableEventDAO();

            SQL.select("" +
                            "SELECT date_from, " +
                            "       date_to, " +
                            "       title, " +
                            "       description, " +
                            "       user_event_type, " +
                            "       user_event_state " +
                            "FROM   user_event " +
                            "WHERE  user_id = UUID_TO_BIN(-userId) " +
                            "AND    -date BETWEEN date_from AND date_to " +
                            "INTO   :from, " +
                            "       :to, " +
                            "       :title, " +
                            "       :description, " +
                            "       :type, " +
                            "       :state ",
                    timetableEventDAO,
                    timetableDAO);

            TimetableUserResponse timetableUserResponse = null;

            if (timetableEventDAO.getFrom() != null) {
                timetableUserResponse = new TimetableUserResponse(
                        timetableEventDAO.getFrom(),
                        timetableEventDAO.getTo(),
                        timetableEventDAO.getTitle(),
                        timetableEventDAO.getDescription(),
                        UserEventType.valueOf(timetableEventDAO.getType()),
                        UserEventState.valueOf(timetableEventDAO.getState())
                );
            }

            timetableEventDAO = new TimetableEventDAO();

            SQL.select("" +
                            "SELECT sce.date_from, " +
                            "       sce.date_to, " +
                            "       sce.title, " +
                            "       sce.description, " +
                            "       sce.school_class_event_type " +
                            "FROM   school_class_event sce " +
                            "INNER JOIN school_class sc ON sce.school_class_id = sc.id " +
                            "INNER JOIN school_class_user scu ON sc.id = scu.school_class_id " +
                            "WHERE  scu.user_id = UUID_TO_BIN(-userId) " +
                            "AND    -date BETWEEN sce.date_from AND sce.date_to " +
                            "INTO   :from, " +
                            "       :to, " +
                            "       :title, " +
                            "       :description, " +
                            "       :type",
                    timetableEventDAO,
                    timetableDAO);

            TimetableSchoolClassResponse timetableSchoolClassResponse = null;

            if (timetableEventDAO.getFrom() != null) {
                timetableSchoolClassResponse = new TimetableSchoolClassResponse(
                        timetableEventDAO.getFrom(),
                        timetableEventDAO.getTo(),
                        timetableEventDAO.getTitle(),
                        timetableEventDAO.getDescription(),
                        SchoolClassEventType.valueOf(timetableEventDAO.getType())
                );
            }

            timetableEventDAO = new TimetableEventDAO();

            SQL.select("" +
                            "SELECT date_from, " +
                            "       date_to, " +
                            "       title, " +
                            "       description, " +
                            "       school_event_type " +
                            "FROM   school_event " +
                            "WHERE  -date BETWEEN date_from AND date_to " +
                            "INTO   :from, " +
                            "       :to, " +
                            "       :title, " +
                            "       :description, " +
                            "       :type",
                    timetableEventDAO,
                    timetableDAO);

            TimetableSchoolResponse timetableSchoolResponse = null;

            if (timetableEventDAO.getFrom() != null) {
                timetableSchoolResponse = new TimetableSchoolResponse(
                        timetableEventDAO.getFrom(),
                        timetableEventDAO.getTo(),
                        timetableEventDAO.getTitle(),
                        timetableEventDAO.getDescription(),
                        SchoolEventType.valueOf(timetableEventDAO.getType())
                );
            }

            List<TimetableLessonDAO> timetableLessonDAOs = new ArrayList<>();

            SQL.select("" +
                            "SELECT BIN_TO_UUID(l.id), " +
                            "       sc.name, " +
                            "       s.name, " +
                            "       BIN_TO_UUID(scs.teacher_id), " +
                            "       BIN_TO_UUID(l.timetable_id), " +
                            "       r.name " +
                            "FROM   lesson l " +
                            "INNER JOIN room r on l.room_id = r.id " +
                            "INNER JOIN timetable t on l.timetable_id = t.id " +
                            "INNER JOIN school_class_subject scs on l.school_class_subject_id = scs.id " +
                            "INNER JOIN subject s on scs.subject_id = s.id " +
                            "INNER JOIN school_class sc on scs.school_class_id = sc.id " +
                            "INNER JOIN school_class_user scu on sc.id = scu.school_class_id " +
                            "WHERE  scu.user_id = UUID_TO_BIN(-userId) " +
                            "AND    CURDATE() BETWEEN scs.start_date AND scs.end_date " +
                            "AND    l.weekday = -weekday " +
                            "INTO   :id, " +
                            "       :schoolClass, " +
                            "       :subject, " +
                            "       :teacherId, " +
                            "       :timetableId, " +
                            "       :room ",
                    timetableLessonDAOs,
                    TimetableLessonDAO.class,
                    timetableDAO);

            List<TimetableLessonResponse> timetableLessonResponses = new ArrayList<>();

            for (TimetableLessonDAO timetableLessonDAO : timetableLessonDAOs) {

                timetableEventDAO = new TimetableEventDAO();
                timetableDAO.setLessonId(timetableLessonDAO.getId());

                SQL.select("" +
                                "SELECT title, " +
                                "       description, " +
                                "       lesson_event_type " +
                                "FROM   lesson_event " +
                                "WHERE  date = DATE(-date) " +
                                "AND    lesson_id = UUID_TO_BIN(-lessonId) " +
                                "INTO   :title, " +
                                "       :description, " +
                                "       :type ",
                        timetableEventDAO,
                        timetableDAO);

                TimetableLessonEventResponse timetableLessonEventResponse = null;

                if (timetableEventDAO.getTitle() != null) {
                    timetableLessonEventResponse = new TimetableLessonEventResponse(
                            timetableEventDAO.getTitle(),
                            timetableEventDAO.getDescription(),
                            LessonEventType.valueOf(timetableEventDAO.getType())
                    );
                }

                timetableLessonResponses.add(new TimetableLessonResponse(
                                timetableLessonDAO.getSchoolClass(),
                                timetableLessonDAO.getSubject(),
                                userService.getExternalUser(access, timetableLessonDAO.getTeacherId()),
                                timetableLessonDAO.getTimetableId(),
                                timetableLessonDAO.getRoom(),
                                timetableLessonEventResponse
                        )
                );
            }

            timetableResponses.add(new TimetableResponse(
                            timetableDAO.getDate(),
                            Weekday.valueOf(timetableDAO.getWeekday()),
                            timetableUserResponse,
                            timetableSchoolClassResponse,
                            timetableSchoolResponse,
                            timetableLessonResponses
                    )
            );
        }

        return timetableResponses;
    }

    public List<TimetableLayoutResponse> getTimetableLayout(String access) {
        authMinRole(access, UserRole.parent);

        List<TimetableLayoutResponse> timetableLayoutResponses = new ArrayList<>();

        SQL.select("" +
                        "SELECT BIN_TO_UUID(id), " +
                        "       start_time, " +
                        "       end_time " +
                        "FROM   timetable " +
                        "INTO   :timetableId, " +
                        "       :start, " +
                        "       :end ",
                timetableLayoutResponses,
                TimetableLayoutResponse.class);

        return timetableLayoutResponses;
    }
}
