package com.vaaaarlos.beerstock.cucumber.stepdef.controller;

import static com.vaaaarlos.beerstock.cucumber.stepdef.CommonStepDefs.INVALID_BEER_ID;
import static com.vaaaarlos.beerstock.cucumber.stepdef.CommonStepDefs.VALID_BEER_ID;
import static com.vaaaarlos.beerstock.util.BeerstockUtils.BASIC_MESSAGE;
import static com.vaaaarlos.beerstock.util.BeerstockUtils.createMessageResponse;
import static com.vaaaarlos.beerstock.util.BeerstockUtils.Operation.DELETED;
import static com.vaaaarlos.beerstock.util.BeerstockUtils.Operation.SAVED;
import static com.vaaaarlos.beerstock.utils.JsonConverter.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import com.vaaaarlos.beerstock.cucumber.context.controller.BeerControllerContextConfiguration;
import com.vaaaarlos.beerstock.cucumber.stepdef.CommonStepDefs;
import com.vaaaarlos.beerstock.dto.request.BeerInsertRequest;
import com.vaaaarlos.beerstock.dto.response.MessageResponse;
import com.vaaaarlos.beerstock.exception.BeerAlreadyExistsException;
import com.vaaaarlos.beerstock.exception.BeerNotFoundException;
import com.vaaaarlos.beerstock.service.BeerService;
import com.vaaaarlos.beerstock.utils.BeerUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@ContextConfiguration(classes = { BeerControllerContextConfiguration.class })
public class BeerControllerStepDefs {

  private static final String BEER_API_URL_PATH = "/api/v1/beers";

  @Autowired @Qualifier("testMockMvc")
  private MockMvc mockMvc;

  @Autowired @Qualifier("mockedBeerService")
  private BeerService beerService;

  private MessageResponse expectedMessageResponse;

  private PageRequest pageRequest = CommonStepDefs.getPageRequest();
  private BeerInsertRequest expectedBeerInsertRequest = CommonStepDefs.getExpectedBeerInsertRequest();

  @When("service save method is called")
  public void service_save_method_is_called() {
    expectedMessageResponse = createMessageResponse(BASIC_MESSAGE, SAVED, VALID_BEER_ID);
    when(beerService.save(expectedBeerInsertRequest)).thenReturn(expectedMessageResponse);
  }

  @When("service save method is called and a Beer already exists with the provided name")
  public void service_save_method_is_called_and_a_beer_already_exists_with_provided_name() {
    when(beerService.save(expectedBeerInsertRequest)).thenThrow(BeerAlreadyExistsException.class);
  }

  @When("service find by id method is called and result is found")
  public void service_find_by_id_method_is_called_and_result_is_found() {
    var expectedBeerResponse = BeerUtils.createFakeBeerResponse();
    when(beerService.findById(VALID_BEER_ID)).thenReturn(expectedBeerResponse);
  }

  @When("service find by id method is called and no result is found")
  public void service_find_by_id_method_is_called_and_no_result_is_found() {
    when(beerService.findById(INVALID_BEER_ID)).thenThrow(BeerNotFoundException.class);
  }

  @When("service delete by id method is called and result is found")
  public void service_delete_by_id_method_is_called_and_result_is_found() {
    expectedMessageResponse = createMessageResponse(BASIC_MESSAGE, DELETED, VALID_BEER_ID);
    when(beerService.deleteById(VALID_BEER_ID)).thenReturn(expectedMessageResponse);
  }

  @When("service delete by id method is called and no result is found")
  public void service_delete_by_id_method_is_called_and_no_result_is_found() {
    when(beerService.deleteById(INVALID_BEER_ID)).thenThrow(BeerNotFoundException.class);
  }

  @When("service find all method is called")
  public void service_find_all_method_is_called() {
    var beerResponseList = Collections.singletonList(BeerUtils.createFakeBeerResponse());
    var expectedPagedBeerResponses = new PageImpl<>(beerResponseList, pageRequest, beerResponseList.size());
    when(beerService.findAll(any(Pageable.class), anyString())).thenReturn(expectedPagedBeerResponses);
  }

  @Then("a success message response should be shown with status code created on post beer")
  public void a_success_message_response_should_be_shown_with_status_code_created_on_post_beer() throws Exception {
    mockMvc.perform(post(BEER_API_URL_PATH)
        .contentType(APPLICATION_JSON)
        .content(asJsonString(expectedBeerInsertRequest)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
  }

  @Then("an error message response should be shown with status code conflict on post beer")
  public void an_error_message_response_should_be_shown_with_status_code_conflict_on_post_beer() throws Exception {
    mockMvc.perform(post(BEER_API_URL_PATH)
        .contentType(APPLICATION_JSON)
        .content(asJsonString(expectedBeerInsertRequest)))
        .andDo(print())
        .andExpect(status().isConflict());
  }

  @Then("an error message response should be shown with status code bad request on post beer")
  public void an_error_message_response_should_be_shown_with_status_code_bad_request_on_post_beer() throws Exception {
    mockMvc.perform(post(BEER_API_URL_PATH)
        .contentType(APPLICATION_JSON)
        .content(asJsonString(expectedBeerInsertRequest)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Then("the corresponding beer entity response should be shown with status code ok on get beer by id")
  public void the_corresponding_beer_entity_response_should_be_shown_with_status_code_ok_on_get_beer_by_id() throws Exception {
    mockMvc.perform(get(String.format("%s/%d", BEER_API_URL_PATH, VALID_BEER_ID))
        .contentType(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)));
  }

  @Then("an error message response should be shown with status code not found on get beer by id")
  public void an_error_message_response_should_be_shown_with_status_code_not_found_on_get_beer_by_id() throws Exception {
    mockMvc.perform(get(String.format("%s/%d", BEER_API_URL_PATH, INVALID_BEER_ID))
        .contentType(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Then("a beer paged response should be shown we status ok on get all beers")
  public void a_beer_paged_response_should_be_shown_we_status_ok_on_get_all_beers() throws Exception {
    mockMvc.perform(get(BEER_API_URL_PATH)
        .contentType(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id", is(1)));
  }

  @Then("a success message response should be shown with status code created on delete beer by id")
  public void a_success_message_response_should_be_shown_with_status_code_created_on_delete_beer_by_id() throws Exception {
    mockMvc.perform(delete(String.format("%s/%d", BEER_API_URL_PATH, VALID_BEER_ID))
        .contentType(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
  }

  @Then("an error message response should be shown with status code not found on delete beer by id")
  public void an_error_message_response_should_be_shown_with_status_code_not_found_on_delete_beer_by_id() throws Exception {
    mockMvc.perform(delete(String.format("%s/%d", BEER_API_URL_PATH, INVALID_BEER_ID))
        .contentType(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

}
