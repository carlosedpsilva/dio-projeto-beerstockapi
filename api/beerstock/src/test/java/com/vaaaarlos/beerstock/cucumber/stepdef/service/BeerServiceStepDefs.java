package com.vaaaarlos.beerstock.cucumber.stepdef.service;

import static com.vaaaarlos.beerstock.cucumber.stepdef.CommonStepDefs.INVALID_BEER_ID;
import static com.vaaaarlos.beerstock.cucumber.stepdef.CommonStepDefs.VALID_BEER_ID;
import static com.vaaaarlos.beerstock.util.BeerstockUtils.BASIC_MESSAGE;
import static com.vaaaarlos.beerstock.util.BeerstockUtils.createMessageResponse;
import static com.vaaaarlos.beerstock.util.BeerstockUtils.Operation.SAVED;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import com.vaaaarlos.beerstock.cucumber.context.service.BeerServiceContextConfiguration;
import com.vaaaarlos.beerstock.cucumber.stepdef.CommonStepDefs;
import com.vaaaarlos.beerstock.dto.request.BeerInsertRequest;
import com.vaaaarlos.beerstock.entity.Beer;
import com.vaaaarlos.beerstock.exception.BeerAlreadyExistsException;
import com.vaaaarlos.beerstock.exception.BeerNotFoundException;
import com.vaaaarlos.beerstock.repository.BeerRepository;
import com.vaaaarlos.beerstock.service.BeerService;
import com.vaaaarlos.beerstock.utils.BeerUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

  private PageRequest pageRequest = CommonStepDefs.getPageRequest();
  private BeerInsertRequest expectedBeerInsertRequest = CommonStepDefs.getExpectedBeerInsertRequest();


  @When("repository find by name method is called and result is found")
  public void repository_find_by_name_method_is_called_and_result_is_found() {
    when(beerRepository.findByName(anyString())).thenReturn(Optional.of(expectedSavedBeer));
  }

  @When("repository find by name method is called and no result is found")
  public void repository_find_by_name_method_is_called_and_no_result_is_found() {
    when(beerRepository.findByName(anyString())).thenReturn(Optional.empty());
  }

  @When("repository find by id method is called and result is found")
  public void repository_find_by_id_method_is_called_and_result_is_found() {
    when(beerRepository.findById(VALID_BEER_ID)).thenReturn(Optional.of(expectedSavedBeer));
  }

  @When("repository find by id method is called and no result is found")
  public void repository_find_by_id_method_is_called_and_no_result_is_found() {
    when(beerRepository.findById(INVALID_BEER_ID)).thenReturn(Optional.empty());
  }

  @When("repository save method is called")
  public void repository_save_method_is_called() {
    when(beerRepository.save(any(Beer.class))).thenReturn(expectedSavedBeer);
  }

  @When("repository find all method is called")
  public void repository_find_all_method_is_called() {
    var beerList = Collections.singletonList(BeerUtils.createFakeEntity());
    var expectedPagedBeers = new PageImpl<>(beerList, pageRequest, beerList.size());
    when(beerRepository.pageAll(any(Pageable.class), nullable(String.class))).thenReturn(expectedPagedBeers);
  }

  @Then("a BeerAlreadyExistsException should be thrown")
  public void a_beer_already_exists_exception_should_be_thrown() {
    assertThrows(BeerAlreadyExistsException.class, () -> beerService.save(expectedBeerInsertRequest));
  }

  @Then("a BeerNotFoundException should be thrown")
  public void a_beer_not_found_exception_should_be_thrown() {
    assertThrows(BeerNotFoundException.class, () -> beerService.findById(INVALID_BEER_ID));
  }

  @Then("a success message response should be returned")
  public void a_success_message_response_should_be_returned() {
    var expectedMessageResponse = createMessageResponse(BASIC_MESSAGE, SAVED, expectedSavedBeer.getId());
    var messageResponse = beerService.save(expectedBeerInsertRequest);
    assertEquals(expectedMessageResponse, messageResponse);
  }

  @Then("the corresponding beer entity response should be returned")
  public void the_corresponding_beer_entity_response_should_be_returned() {
    var expectedBeerResponse = BeerUtils.createFakeBeerResponse();
    var beerResponse = beerService.findById(VALID_BEER_ID);
    assertEquals(expectedBeerResponse, beerResponse);
  }

  @Then("a beer paged response should be returned")
  public void a_beer_paged_response_should_be_returned() {
    var beerResponseList = Collections.singletonList(BeerUtils.createFakeBeerResponse());
    var expectedPagedBeerResponses = new PageImpl<>(beerResponseList, pageRequest, beerResponseList.size());
    var pagedBeerResponses = beerService.findAll(pageRequest, "");
    assertEquals(expectedPagedBeerResponses, pagedBeerResponses);
  }

}
