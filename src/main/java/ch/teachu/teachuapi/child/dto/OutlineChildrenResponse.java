package ch.teachu.teachuapi.child.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OutlineChildrenResponse {
    private List<OutlineChildDTO> outlineChildren;
}
