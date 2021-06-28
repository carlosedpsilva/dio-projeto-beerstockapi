package com.vaaaarlos.beerstock.exception;

import com.vaaaarlos.beerstock.exception.meta.CustomApiException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeerStockExceededException extends CustomApiException {

  public BeerStockExceededException(long id, int quantity, int increment, int capacity) {
    super(String.format("Beer with ID %d and quantity %d cannot be incremented by %d "
        + "because it exceeds the max stock capacity of %d", id, quantity, increment, capacity));
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.BAD_REQUEST;
  }

}
