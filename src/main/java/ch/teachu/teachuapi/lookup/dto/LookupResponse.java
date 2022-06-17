package ch.teachu.teachuapi.lookup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LookupResponse {
    private List<LookupEntryResponse> lookups;
}
