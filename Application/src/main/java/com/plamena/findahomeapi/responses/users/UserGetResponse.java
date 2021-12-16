package com.plamena.findahomeapi.responses.users;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserGetResponse implements Serializable {

  private String userId;
  private String email;
  private String firstName;
  private String lastName;
  private Timestamp createdOn;
  private List<RoleGetResponse> roles;
}
