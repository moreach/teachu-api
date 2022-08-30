package ch.teachu.teachuapi.absence;

import ch.teachu.teachuapi.absence.dtos.AbsenceDAO;
import ch.teachu.teachuapi.absence.dtos.AbsenceRequest;
import ch.teachu.teachuapi.absence.dtos.AbsenceResponse;
import ch.teachu.teachuapi.shared.AbstractService;
import ch.teachu.teachuapi.shared.dtos.MessageResponse;
import ch.teachu.teachuapi.shared.dtos.SharedDAO;
import ch.teachu.teachuapi.shared.enums.UserEventState;
import ch.teachu.teachuapi.shared.enums.UserEventType;
import ch.teachu.teachuapi.shared.errorhandlig.InvalidException;
import ch.teachu.teachuapi.sql.SQL;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AbsenceService extends AbstractService {
    public List<AbsenceResponse> getAbsences(String access, String studentId) {
        SharedDAO sharedDAO = authStudentId(access, studentId);

        List<AbsenceDAO> absenceDAOs = new ArrayList<>();

        SQL.select("" +
                        "SELECT BIN_TO_UUID(id), " +
                        "       date_from, " +
                        "       date_to, " +
                        "       title, " +
                        "       description, " +
                        "       user_event_type, " +
                        "       user_event_state " +
                        "FROM   user_event " +
                        "WHERE  user_id = UUID_TO_BIN(-userId) " +
                        "INTO   :id, " +
                        "       :from, " +
                        "       :to, " +
                        "       :title, " +
                        "       :description, " +
                        "       :type, " +
                        "       :state ",
                absenceDAOs,
                AbsenceDAO.class,
                sharedDAO);

        List<AbsenceResponse> absenceResponses = new ArrayList<>();

        for (AbsenceDAO absenceDAO : absenceDAOs) {
            absenceResponses.add(new AbsenceResponse(
                            absenceDAO.getId(),
                            absenceDAO.getFrom(),
                            absenceDAO.getTo(),
                            absenceDAO.getTitle(),
                            absenceDAO.getDescription(),
                            UserEventType.valueOf(absenceDAO.getType()),
                            UserEventState.valueOf(absenceDAO.getState())
                    )
            );
        }

        return absenceResponses;
    }


    public MessageResponse createAbsence(String access, String studentId, AbsenceRequest absenceRequest) {
        SharedDAO sharedDAO = authStudentId(access, studentId);

        AbsenceDAO absenceDAO = new AbsenceDAO();
        absenceDAO.setUserId(sharedDAO.getUserId());
        absenceDAO.setFrom(absenceRequest.getFrom());
        absenceDAO.setTo(absenceRequest.getTo());
        absenceDAO.setTitle(absenceRequest.getTitle());
        absenceDAO.setDescription(absenceRequest.getDescription());
        absenceDAO.setType(absenceRequest.getType().name());

        if (sharedDAO.getIsParent() == null || !sharedDAO.getIsParent()) {
            absenceDAO.setState(UserEventState.pending.name());
        } else {
            absenceDAO.setState(UserEventState.verified.name());
        }

        int count = SQL.insert("" +
                        "INSERT INTO user_event ( " +
                        "       id, " +
                        "       user_id, " +
                        "       date_from, " +
                        "       date_to, " +
                        "       title, " +
                        "       description, " +
                        "       user_event_type, " +
                        "       user_event_state) " +
                        "VALUES (" +
                        "       UUID_TO_BIN(UUID()), " +
                        "       UUID_TO_BIN(-userId), " +
                        "       -from, " +
                        "       -to, " +
                        "       -title, " +
                        "       -description, " +
                        "       -type, " +
                        "       -state) ",
                absenceDAO);

        if (count == 0) {
            throw new RuntimeException("Failed to create absence");
        }

        return new MessageResponse("Successfully created absence");
    }

    public MessageResponse updateAbsence(String access, String studentId, String absenceId, AbsenceRequest absenceRequest) {
        SharedDAO sharedDAO = authStudentId(access, studentId);

        AbsenceDAO absenceDAO = new AbsenceDAO();
        absenceDAO.setId(absenceId);
        absenceDAO.setFrom(absenceRequest.getFrom());
        absenceDAO.setTo(absenceRequest.getTo());
        absenceDAO.setTitle(absenceRequest.getTitle());
        absenceDAO.setDescription(absenceRequest.getDescription());
        absenceDAO.setType(absenceRequest.getType().name());

        if (sharedDAO.getIsParent() == null || !sharedDAO.getIsParent()) {
            absenceDAO.setState(UserEventState.pending.name());
        } else {
            absenceDAO.setState(UserEventState.verified.name());
        }

        int count = SQL.update("" +
                        "UPDATE user_event " +
                        "SET    date_from = -from, " +
                        "       date_to = -to, " +
                        "       title = -title, " +
                        "       description = -description, " +
                        "       user_event_type = -type, " +
                        "       user_event_state = -state " +
                        "WHERE  id = UUID_TO_BIN(-id) ",
                absenceDAO);

        if (count == 0) {
            throw new RuntimeException("Failed to update absence");
        }

        return new MessageResponse("Successfully updated absence");
    }

    public MessageResponse verifyAbsence(String access, String studentId, String absenceId) {
        SharedDAO sharedDAO = authStudentId(access, studentId);

        if (!sharedDAO.getIsParent()) {
            throw new InvalidException("Student cannot verify absences");
        }

        AbsenceDAO absenceDAO = new AbsenceDAO();
        absenceDAO.setId(absenceId);
        absenceDAO.setState(UserEventState.verified.name());

        int count = SQL.update("" +
                        "UPDATE user_event " +
                        "SET    user_event_state = -state " +
                        "WHERE  id = UUID_TO_BIN(-id) ",
                absenceDAO);

        if (count == 0) {
            throw new RuntimeException("Failed to update absence");
        }

        return new MessageResponse("Successfully verified absence");
    }
}
