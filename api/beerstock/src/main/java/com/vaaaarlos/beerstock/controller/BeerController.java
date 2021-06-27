package com.vaaaarlos.beerstock.controller;

import javax.validation.Valid;

import com.vaaaarlos.beerstock.dto.request.BeerInsertRequest;
import com.vaaaarlos.beerstock.dto.response.MessageResponse;
import com.vaaaarlos.beerstock.service.BeerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/beers")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BeerController {

  private final BeerService beerService;

  /*
   * POST OPERATION
   */

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public MessageResponse save(@RequestBody @Valid BeerInsertRequest beerInsertRequest) {
    return beerService.save(beerInsertRequest);
  }
}
