package com.lasmch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationFailureException extends RuntimeException {

  public ValidationFailureException() {
    super();
  }

  public ValidationFailureException(String msg) {
    super(msg);
  }

  public ValidationFailureException(String msg, Throwable t) {
    super(msg, t);
  }

}