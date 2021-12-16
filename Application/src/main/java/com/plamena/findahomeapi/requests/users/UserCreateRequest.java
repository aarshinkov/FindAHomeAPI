package com.plamena.findahomeapi.requests.users;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCreateRequest implements Serializable {

  private String email;
  private String password;
  private String firstName;
  private String lastName;
}
