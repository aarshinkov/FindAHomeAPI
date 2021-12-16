package com.plamena.findahomeapi.exceptions.handlers;

import com.plamena.findahomeapi.exceptions.FHException;
import com.plamena.findahomeapi.exceptions.responses.FCErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionsHandler {

  @ExceptionHandler(FHException.class)
  public ResponseEntity<FCErrorResponse> mcdErrorHandler(FHException ex, WebRequest request) {

    FCErrorResponse error = new FCErrorResponse();
    error.setMessage(ex.getMessage());
    error.setCode(ex.getCode());
    error.setDetails(ex.getDetails());
    error.setTimestamp(LocalDateTime.now());

    return new ResponseEntity<>(error, ex.getStatus());
  }
}
