package com.plamena.findahomeapi.requests.users;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleAssignRequest implements Serializable {

  private String role;
}
