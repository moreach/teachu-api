package ch.teachu.teachuapi.classlist.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassListDAO {
    private String access;

    private String id;
    private String name;
    private String teacherId;
    private String userId;
}