package com.vaaaarlos.beerstock.exception.meta;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomApiException extends RuntimeException {

  private HttpStatus status;

  public CustomApiException() {
    super();
  }

  public CustomApiException(String message) {
    super(message);
  }

}
