package com.vaaaarlos.beerstock.controller;

import javax.validation.Valid;

import com.vaaaarlos.beerstock.controller.docs.BeerControllerDocs;
import com.vaaaarlos.beerstock.dto.request.BeerInsertRequest;
import com.vaaaarlos.beerstock.dto.response.BeerResponse;
import com.vaaaarlos.beerstock.dto.response.MessageResponse;
import com.vaaaarlos.beerstock.exception.BeerAlreadyExistsException;
import com.vaaaarlos.beerstock.exception.BeerNotFoundException;
import com.vaaaarlos.beerstock.service.BeerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/beers")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BeerController implements BeerControllerDocs {

  private final BeerService beerService;

  /*
   * POST OPERATION
   */

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public MessageResponse save(@RequestBody @Valid BeerInsertRequest beerInsertRequest) throws BeerAlreadyExistsException {
    return beerService.save(beerInsertRequest);
  }

  /*
   * GET OPERATION
   */

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public BeerResponse findById(@PathVariable long id) throws BeerNotFoundException {
    return beerService.findById(id);
  }

}
