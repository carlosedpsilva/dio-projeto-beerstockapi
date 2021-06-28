package com.vaaaarlos.beerstock.exception.meta;

import org.springframework.http.HttpStatus;

public abstract class CustomApiException extends RuntimeException {

  protected CustomApiException(String message) {
    super(message);
  }

  public abstract HttpStatus getStatus();

}
