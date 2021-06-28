package com.vaaaarlos.beerstock.exception;

import com.vaaaarlos.beerstock.exception.meta.CustomApiException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BeerAlreadyExistsException extends CustomApiException {

  public BeerAlreadyExistsException(String name) {
    super(String.format("Beer with name '%s' already exists", name));
    setStatus(HttpStatus.CONFLICT);
  }

}
