package com.vaaaarlos.beerstock.cucumber.stepdef.service;

import static com.vaaaarlos.beerstock.util.BeerstockUtils.BASIC_MESSAGE;
import static com.vaaaarlos.beerstock.util.BeerstockUtils.createMessageResponse;
import static com.vaaaarlos.beerstock.util.BeerstockUtils.Operation.SAVED;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.vaaaarlos.beerstock.cucumber.context.service.BeerServiceContextConfiguration;
import com.vaaaarlos.beerstock.cucumber.stepdef.CommonStepDefs;
import com.vaaaarlos.beerstock.dto.request.BeerInsertRequest;
import com.vaaaarlos.beerstock.entity.Beer;
import com.vaaaarlos.beerstock.exception.BeerAlreadyExistsException;
import com.vaaaarlos.beerstock.repository.BeerRepository;
import com.vaaaarlos.beerstock.service.BeerService;
import com.vaaaarlos.beerstock.utils.BeerUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@ContextConfiguration(classes = { BeerServiceContextConfiguration.class })
public class BeerServiceStepDefs {

  @Autowired @Qualifier("testBeerService")
  private BeerService beerService;

  @Autowired @Qualifier("mockedBeerRepository")
  private BeerRepository beerRepository;

  private Beer expectedSavedBeer = BeerUtils.createFakeEntity();
  private BeerInsertRequest expectedBeerInsertRequest = CommonStepDefs.getExpectedBeerInsertRequest();

  @When("repository find by name method is called and result is found")
  public void repository_find_by_name_method_is_called_and_result_is_found() {
    when(beerRepository.findByName(expectedBeerInsertRequest.getName())).thenReturn(Optional.of(expectedSavedBeer));
  }

  @When("repository find by name method is called and no result is found")
  public void repository_find_by_name_method_is_called_and_no_result_is_found() {
    when(beerRepository.findByName(expectedBeerInsertRequest.getName())).thenReturn(Optional.empty());
  }

  @When("repository save method is called")
  public void repository_save_method_is_called() {
    when(beerRepository.save(any(Beer.class))).thenReturn(expectedSavedBeer);
  }

  @Then("a BeerAlreadyExistsException should be thrown")
  public void a_beer_already_exists_exception_should_be_thrown() {
    assertThrows(BeerAlreadyExistsException.class, () -> beerService.save(expectedBeerInsertRequest));
  }

  @Then("a success message response should be returned")
  public void a_success_message_response_should_be_returned() {
    var expectedMessageResponse = createMessageResponse(BASIC_MESSAGE, SAVED, expectedSavedBeer.getId());
    var messageResponse = beerService.save(expectedBeerInsertRequest);
    assertEquals(expectedMessageResponse, messageResponse);
  }

}
