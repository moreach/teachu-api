package ch.teachu.teachuapi.schoolinfo;

import ch.teachu.teachuapi.parent.AbstractService;
import ch.teachu.teachuapi.parent.enums.UserRole;
import ch.teachu.teachuapi.schoolinfo.dtos.SchoolInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SchoolInfoService extends AbstractService {
    public ResponseEntity<List<SchoolInfoResponse>> getSchoolInfos(String access) {
        authMinRole(access, UserRole.parent);

        // todo get data + check if active

        SchoolInfoResponse schoolInfoResponse1 = new SchoolInfoResponse(
                "mockTitle",
                "mockMessage",
                false,
                false,
                new Date(),
                "mockCreatorName"
        );

        SchoolInfoResponse schoolInfoResponse2 = new SchoolInfoResponse(
                "mockTitle",
                "mockMessage",
                true,
                false,
                new Date(),
                "mockCreatorName"
        );

        SchoolInfoResponse schoolInfoResponse3 = new SchoolInfoResponse(
                "mockTitle",
                "mockMessage",
                false,
                true,
                new Date(),
                "mockCreatorName"
        );

        List<SchoolInfoResponse> schoolInfoResponses = new ArrayList();
        schoolInfoResponses.add(schoolInfoResponse1);
        schoolInfoResponses.add(schoolInfoResponse2);
        schoolInfoResponses.add(schoolInfoResponse3);

        return ResponseEntity.ok(schoolInfoResponses);
    }
}
