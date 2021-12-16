package com.plamena.findahomeapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class FHException extends RuntimeException {

  private Integer code;
  private String message;
  private String details;
  private HttpStatus status;

  public FHException(String message) {
    super(message);
  }
}
