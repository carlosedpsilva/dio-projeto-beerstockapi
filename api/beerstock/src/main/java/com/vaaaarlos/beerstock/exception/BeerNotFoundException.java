package com.vaaaarlos.beerstock.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.server.ResponseStatusException;

public class BeerNotFoundException extends ResponseStatusException {

  public BeerNotFoundException(long id) {
    super(NOT_FOUND, String.format("No Beer found with ID '%d'", id));
  }

  public BeerNotFoundException(String name) {
    super(NOT_FOUND, String.format("No Beer found with name '%s'", name));
  }


}
