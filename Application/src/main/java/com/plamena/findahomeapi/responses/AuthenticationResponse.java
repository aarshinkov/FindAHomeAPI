package com.plamena.findahomeapi.responses;

import com.plamena.findahomeapi.responses.users.UserGetResponse;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthenticationResponse implements Serializable {

  private String tokenType;
  private String accessToken;
  private List<String> access;
  private UserGetResponse user;
}
