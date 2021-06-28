package com.vaaaarlos.beerstock.cucumber.stepdef;

import com.vaaaarlos.beerstock.dto.request.BeerInsertRequest;
import com.vaaaarlos.beerstock.utils.BeerUtils;

import org.springframework.data.domain.PageRequest;

import io.cucumber.java.en.Given;
import lombok.Getter;

public class CommonStepDefs {

  public static final long VALID_BEER_ID = 1L;
  public static final long INVALID_BEER_ID = 2L;

  public static final int VALID_BEER_INCREMENT = 40;
  public static final int INVALID_BEER_INCREMENT = 41;

  @Getter
  private static BeerInsertRequest expectedBeerInsertRequest;

  @Getter
  private static PageRequest pageRequest = PageRequest.of(0, 8);

  @Given("a valid Beer insert request is provided")
  public void a_valid_beer_insert_request_is_provided() {
    expectedBeerInsertRequest = BeerUtils.createFakeBeerInsertRequest();
  }

  @Given("an invalid Beer insert request is provided")
  public void an_invalid_beer_insert_request_is_provided() {
    expectedBeerInsertRequest = new BeerInsertRequest();
  }

}
