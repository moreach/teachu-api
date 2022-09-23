package ch.teachu.teachuapi.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSyncDataRequest {
  private String userId;
  private String firstname;
  private String lastname;
  private String birthdate;
  private String profileImageId;
}
