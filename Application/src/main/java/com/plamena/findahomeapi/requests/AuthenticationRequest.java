package com.plamena.findahomeapi.requests;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthenticationRequest implements Serializable {

  private String email;
  private String password;
}
