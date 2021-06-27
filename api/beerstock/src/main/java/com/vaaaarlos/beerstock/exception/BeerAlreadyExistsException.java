package com.vaaaarlos.beerstock.exception;

import static org.springframework.http.HttpStatus.CONFLICT;

import org.springframework.web.server.ResponseStatusException;

public class BeerAlreadyExistsException extends ResponseStatusException {

  public BeerAlreadyExistsException(String name) {
    super(CONFLICT, String.format("Beer with name '%s' already exists", name));
  }

}
