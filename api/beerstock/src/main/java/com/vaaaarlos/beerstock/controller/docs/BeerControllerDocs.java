package com.vaaaarlos.beerstock.controller.docs;



import com.vaaaarlos.beerstock.dto.request.BeerInsertRequest;
import com.vaaaarlos.beerstock.dto.response.MessageResponse;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api("Manages a Beer Stock")
public interface BeerControllerDocs {

  @Operation(summary = "Beer save operation")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Beer saved"),
    @ApiResponse(responseCode = "400", description = "Missing required fields or wrong field range value"),
    @ApiResponse(responseCode = "409", description = "Beer with provided name already exists.")
  })
  public MessageResponse save(BeerInsertRequest beerInsertRequest);

}
