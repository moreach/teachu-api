package ch.teachu.teachuapi.schoolinfo;

import ch.teachu.teachuapi.schoolinfo.dtos.SchoolInfoDAO;
import ch.teachu.teachuapi.schoolinfo.dtos.SchoolInfoResponse;
import ch.teachu.teachuapi.shared.AbstractService;
import ch.teachu.teachuapi.shared.enums.UserRole;
import ch.teachu.teachuapi.sql.SQL;
import ch.teachu.teachuapi.user.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolInfoService extends AbstractService {

    private final UserService userService;

    public SchoolInfoService(UserService userService) {
        this.userService = userService;
    }

    public List<SchoolInfoResponse> getSchoolInfos(String access) {
        authMinRole(access, UserRole.parent);

        List<SchoolInfoDAO> schoolInfoDAOs = new ArrayList<>();

        SQL.select("" +
                        "SELECT title, " +
                        "       message, " +
                        "       date, " +
                        "       BIN_TO_UUID(img), " +
                        "       BIN_TO_UUID(user_id)," +
                        "       important, " +
                        "       pinned " +
                        "FROM   school_info " +
                        "WHERE  active IS TRUE " +
                        "INTO   :title, " +
                        "       :message, " +
                        "       :date, " +
                        "       :imageId, " +
                        "       :userId, " +
                        "       :important, " +
                        "       :pinned ",
                schoolInfoDAOs,
                SchoolInfoDAO.class);

        List<SchoolInfoResponse> schoolInfoResponses = new ArrayList<>();

        for (SchoolInfoDAO schoolInfoDAO : schoolInfoDAOs) {
            schoolInfoResponses.add(new SchoolInfoResponse(
                            schoolInfoDAO.getTitle(),
                            schoolInfoDAO.getMessage(),
                            schoolInfoDAO.getImportant(),
                            schoolInfoDAO.getPinned(),
                            schoolInfoDAO.getDate(),
                            userService.getExternalUser(access, schoolInfoDAO.getUserId()),
                            schoolInfoDAO.getImageId()
                    )
            );
        }

        return schoolInfoResponses;
    }
}
