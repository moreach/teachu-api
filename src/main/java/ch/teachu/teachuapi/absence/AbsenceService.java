package ch.teachu.teachuapi.absence;

import ch.teachu.teachuapi.absence.dtos.AbsenceResponse;
import ch.teachu.teachuapi.shared.AbstractService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbsenceService extends AbstractService {
    public ResponseEntity<List<AbsenceResponse>> getAbsences(String access) {
        return null;
    }
}
