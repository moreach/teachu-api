package ch.teachu.teachuapi.schoolinfo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchoolInfoDAO {
    private String title;
    private String message;
    private Boolean important;
    private Boolean pinned;
    private Date date;
    private String userId;
}