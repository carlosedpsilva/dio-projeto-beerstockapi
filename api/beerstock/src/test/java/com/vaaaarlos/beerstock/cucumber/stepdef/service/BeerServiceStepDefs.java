package com.vaaaarlos.beerstock.cucumber.stepdef.service;

import static com.vaaaarlos.beerstock.cucumber.stepdef.CommonStepDefs.INVALID_BEER_ID;
import static com.vaaaarlos.beerstock.cucumber.stepdef.CommonStepDefs.INVALID_BEER_INCREMENT;
import static com.vaaaarlos.beerstock.cucumber.stepdef.CommonStepDefs.VALID_BEER_ID;
import static com.vaaaarlos.beerstock.cucumber.stepdef.CommonStepDefs.VALID_BEER_INCREMENT;
import static com.vaaaarlos.beerstock.util.BeerstockUtils.BASIC_MESSAGE;
import static com.vaaaarlos.beerstock.util.BeerstockUtils.INCREMENT_MESSAGE;
import static com.vaaaarlos.beerstock.util.BeerstockUtils.createMessageResponse;
import static com.vaaaarlos.beerstock.util.BeerstockUtils.Operation.DELETED;
import static com.vaaaarlos.beerstock.util.BeerstockUtils.Operation.SAVED;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import com.vaaaarlos.beerstock.cucumber.context.service.BeerServiceContextConfiguration;
import com.vaaaarlos.beerstock.cucumber.stepdef.CommonStepDefs;
import com.vaaaarlos.beerstock.dto.request.BeerInsertRequest;
import com.vaaaarlos.beerstock.entity.Beer;
import com.vaaaarlos.beerstock.exception.BeerAlreadyExistsException;
import com.vaaaarlos.beerstock.exception.BeerNotFoundException;
import com.vaaaarlos.beerstock.exception.BeerStockExceededException;
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

  private PageRequest pageRequest = CommonStepDefs.getPageRequest();
  private Beer expectedSavedBeer = CommonStepDefs.getExpectedSavedBeer();
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

  @When("repository delete by id method is called")
  public void repository_delete_by_id_method_is_called() {
    doNothing().when(beerRepository).deleteById(VALID_BEER_ID);
  }

  @Then("a BeerAlreadyExistsException should be thrown on save beer")
  public void a_beer_already_exists_exception_should_be_thrown_on_save_beer() {
    assertThrows(BeerAlreadyExistsException.class, () -> beerService.save(expectedBeerInsertRequest));
  }

  @Then("a BeerNotFoundException should be thrown on find beer by id")
  public void a_beer_not_found_exception_should_be_thrown_on_find_beer_by_id() {
    assertThrows(BeerNotFoundException.class, () -> beerService.findById(INVALID_BEER_ID));
  }

  @Then("a BeerNotFoundException should be thrown on delete beer by id")
  public void a_beer_not_found_exception_should_be_thrown_on_delete_beer_by_id() {
    assertThrows(BeerNotFoundException.class, () -> beerService.deleteById(INVALID_BEER_ID));
  }

  @Then("a BeerNotFoundException should be thrown on increment beer quantity")
  public void a_beer_not_found_exception_should_be_thrown_on_increment_beer_quantity() {
    assertThrows(BeerNotFoundException.class, () -> beerService.incrementBeerQuantity(INVALID_BEER_ID, VALID_BEER_INCREMENT));
  }

  @Then("a BeerStockExceededException should be thrown on increment beer quantity")
  public void a_beer_stock_exceeded_exception_should_be_thrown_on_increment_beer_quantity() {
    assertThrows(BeerStockExceededException.class, () -> beerService.incrementBeerQuantity(VALID_BEER_ID, INVALID_BEER_INCREMENT));

  }

  @Then("a success message response should be returned on save beer")
  public void a_success_message_response_should_be_returned_on_save_beer() {
    var expectedMessageResponse = createMessageResponse(BASIC_MESSAGE, SAVED, expectedSavedBeer.getId());
    var messageResponse = beerService.save(expectedBeerInsertRequest);
    assertEquals(expectedMessageResponse, messageResponse);
  }

  @Then("the corresponding beer entity response should be returned on find beer by id")
  public void the_corresponding_beer_entity_response_should_be_returned_on_find_beer_by_id() {
    var expectedBeerResponse = BeerUtils.createFakeBeerResponse();
    var beerResponse = beerService.findById(VALID_BEER_ID);
    assertEquals(expectedBeerResponse, beerResponse);
  }

  @Then("a beer paged response should be returned on find all beers")
  public void a_beer_paged_response_should_be_returned_on_find_all_beers() {
    var beerResponseList = Collections.singletonList(BeerUtils.createFakeBeerResponse());
    var expectedPagedBeerResponses = new PageImpl<>(beerResponseList, pageRequest, beerResponseList.size());
    var pagedBeerResponses = beerService.findAll(pageRequest, "");
    assertEquals(expectedPagedBeerResponses, pagedBeerResponses);
  }

  @Then("a success message response should be returned on delete beer by id")
  public void a_success_message_response_should_be_returned_on_delete_beer_by_id() {
    var messageResponse = beerService.deleteById(VALID_BEER_ID);
    var expectedMessageResponse = createMessageResponse(BASIC_MESSAGE, DELETED, VALID_BEER_ID);
    assertEquals(expectedMessageResponse, messageResponse);
  }

  @Then("a success message response should be shown with status code ok on increment beer quantity")
  public void a_success_message_response_should_be_shown_with_status_code_ok_on_increment_beer_quantity() {
    var expectedQquantity = expectedSavedBeer.getQuantity();
    var expectedFinalQuantity = expectedQquantity + VALID_BEER_INCREMENT;
    var messageResponse = beerService.incrementBeerQuantity(VALID_BEER_ID, VALID_BEER_INCREMENT);
    var expectedMessageResponse = createMessageResponse(INCREMENT_MESSAGE, VALID_BEER_ID, expectedQquantity, expectedFinalQuantity);
    assertEquals(expectedMessageResponse, messageResponse);
  }

}
