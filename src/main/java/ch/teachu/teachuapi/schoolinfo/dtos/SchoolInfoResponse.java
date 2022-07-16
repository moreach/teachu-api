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
public class SchoolInfoResponse {
    private String title;
    private String message;
    private boolean important;
    private boolean pinned;
    private Date date;
    private String creatorName;
    // todo to be implemented
    // private IDK image;
}