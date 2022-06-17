package ch.teachu.teachuapi.child.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SemesterResponse {

    public static final String ID = "id";
    public static final String NAME = "name";

    private UUID id;
    private String name;
}
