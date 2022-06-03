package ch.teachu.teachuapi.lookup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectLookupRequest extends LookupRequest {
    private UUID schoolClass;
    private boolean onlyLoadOwnSubjects;
}
