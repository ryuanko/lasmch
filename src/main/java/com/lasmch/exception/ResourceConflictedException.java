package com.lasmch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceConflictedException extends RuntimeException {

  public ResourceConflictedException() {
    super();
  }

  public ResourceConflictedException(String msg) {
    super(msg);
  }

  public ResourceConflictedException(String msg, Throwable t) {
    super(msg, t);
  }


}