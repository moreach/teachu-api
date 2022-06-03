package ch.teachu.teachuapi.lookup;

import ch.teachu.teachuapi.daos.AuthDAO;
import ch.teachu.teachuapi.enums.UserRole;
import ch.teachu.teachuapi.lookup.dto.*;
import ch.teachu.teachuapi.parent.AbstractService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class LookupService extends AbstractService {

    private final LookupRepo lookupRepo;

    public LookupService(LookupRepo lookupRepo) {
        this.lookupRepo = lookupRepo;
    }

    public ResponseEntity<LookupResponse> lookupSemesters(String auth, LookupRequest lookupRequest) {
        authMinRole(auth, UserRole.PARENT);
        return ResponseEntity.ok(lookupRepo.lookupSemesters(lookupRequest));
    }

    public ResponseEntity<LookupResponse> lookupSubjects(String auth, SubjectLookupRequest lookupRequest) {
        AuthDAO authDAO = authMinRole(auth, UserRole.PARENT);
        Assert.isTrue(!lookupRequest.isOnlyLoadOwnSubjects() || authDAO.getRole() == UserRole.TEACHER, "Can only load own subjects as a teacher.");
        return ResponseEntity.ok(lookupRepo.lookupSubjects(lookupRequest, authDAO.getUserId()));
    }

    public ResponseEntity<LookupResponse> lookupSchoolClass(String auth, LookupSchoolClassRequest lookupRequest) {
        AuthDAO authDAO = authExactRole(auth, UserRole.TEACHER);
        return ResponseEntity.ok(lookupRepo.lookupSchoolClass(lookupRequest, authDAO.getUserId()));
    }

    public ResponseEntity<LookupResponse> lookupStudent(String auth, LookupStudentRequest request) {
        authMinRole(auth, UserRole.PARENT);
        return ResponseEntity.ok(lookupRepo.lookupStudent(request));
    }
}
