package com.vaaaarlos.beerstock.cucumber.stepdef;

import com.vaaaarlos.beerstock.dto.request.BeerInsertRequest;
import com.vaaaarlos.beerstock.utils.BeerUtils;

import io.cucumber.java.en.Given;
import lombok.Getter;

public class CommonStepDefs {

  @Getter
  private static BeerInsertRequest expectedBeerInsertRequest;

  @Given("a valid Beer insert request is provided")
  public void a_valid_beer_insert_request_is_provided() {
    expectedBeerInsertRequest = BeerUtils.createFakeBeerInsertRequest();
  }

  @Given("an invalid Beer insert request is provided")
  public void an_invalid_beer_insert_request_is_provided() {
    expectedBeerInsertRequest = new BeerInsertRequest();
  }

}
