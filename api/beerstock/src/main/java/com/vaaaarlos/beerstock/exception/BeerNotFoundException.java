package com.vaaaarlos.beerstock.exception;

import com.vaaaarlos.beerstock.exception.meta.CustomApiException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BeerNotFoundException extends CustomApiException {

  public BeerNotFoundException(long id) {
    super(String.format("No Beer found with ID %d", id));
  }

  public BeerNotFoundException(String name) {
    super(String.format("No Beer found with name '%s'", name));
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }


}
