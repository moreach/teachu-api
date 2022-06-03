package ch.teachu.teachuapi.lookup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LookupSchoolClassRequest extends LookupRequest {
    private boolean onlyLoadOwnSchoolClasses;
}
