package com.plamena.findahomeapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthException extends RuntimeException {

  private Integer code;
  private String message;
  private String details;
}
