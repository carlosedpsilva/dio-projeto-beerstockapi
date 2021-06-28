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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<BeerResponse> findAll(
    @RequestParam(value =         "page", defaultValue =   "0") Integer page,
    @RequestParam(value = "linesPerPage", defaultValue =   "8") Integer linesPerPage,
    @RequestParam(value =    "direction", defaultValue = "ASC")  String direction,
    @RequestParam(value =      "orderBy", defaultValue =  "id")  String orderBy,
    @RequestParam(value =         "name", defaultValue =    "")  String name
  ) {
    var pageRequest = PageRequest.of(0, 8, Direction.valueOf(direction), orderBy);
    return beerService.findAll(pageRequest, name);
  }

  /*
   * DELETE OPERATION
   */

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public MessageResponse deleteById(@PathVariable long id) throws BeerNotFoundException {
    return beerService.deleteById(id);
  }

  /*
   * DELETE OPERATION
   */

  @PatchMapping("{id}/increment/{increment}")
  @ResponseStatus(HttpStatus.OK)
  public MessageResponse incrementBeerQuantity(@PathVariable long id, @PathVariable int increment) throws BeerNotFoundException {
    return beerService.incrementBeerQuantity(id, increment);
  }

}
