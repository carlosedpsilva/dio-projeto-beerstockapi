package com.vaaaarlos.beerstock.service;

import static com.vaaaarlos.beerstock.util.BeerstockUtils.BASIC_MESSAGE;
import static com.vaaaarlos.beerstock.util.BeerstockUtils.Operation.DELETED;
import static com.vaaaarlos.beerstock.util.BeerstockUtils.Operation.SAVED;

import com.vaaaarlos.beerstock.dto.mapper.BeerMapper;
import com.vaaaarlos.beerstock.dto.request.BeerInsertRequest;
import com.vaaaarlos.beerstock.dto.response.BeerResponse;
import com.vaaaarlos.beerstock.dto.response.MessageResponse;
import com.vaaaarlos.beerstock.entity.Beer;
import com.vaaaarlos.beerstock.exception.BeerAlreadyExistsException;
import com.vaaaarlos.beerstock.exception.BeerNotFoundException;
import com.vaaaarlos.beerstock.repository.BeerRepository;
import com.vaaaarlos.beerstock.util.BeerstockUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BeerService {

  private final BeerRepository beerRepository;
  private final BeerMapper beerMapper;

  /*
   * POST OPERATION
   */

  public MessageResponse save(BeerInsertRequest beerInsertRequest) throws BeerAlreadyExistsException {
    verifyIfExists(beerInsertRequest.getName());
    var beerToSave = beerMapper.toModel(beerInsertRequest);
    var savedBeer = beerRepository.save(beerToSave);
    return BeerstockUtils.createMessageResponse(BASIC_MESSAGE, SAVED, savedBeer.getId());
  }

  /*
   * GET OPERATION
   */

  public BeerResponse findById(long id) throws BeerNotFoundException {
    var savedBeer = verifyIfExists(id);
    return beerMapper.toBeerResponse(savedBeer);
  }

  public Page<BeerResponse> findAll(Pageable pageRequest, String name) {
    name = name.isBlank() ? null : name;
    var pagedBeers = beerRepository.pageAll(pageRequest, name);
    return pagedBeers.map(beerMapper::toBeerResponse);
  }

  /*
   * DELETE OPERATION
   */

  public MessageResponse deleteById(long id) throws BeerNotFoundException {
    verifyIfExists(id);
    beerRepository.deleteById(id);
    return BeerstockUtils.createMessageResponse(BASIC_MESSAGE, DELETED, id);
  }

  /*
   * OTHER
   */

  public Beer verifyIfExists(long id) throws BeerNotFoundException {
    return beerRepository.findById(id).orElseThrow(() -> new BeerNotFoundException(id));
  }

  public void verifyIfExists(String name) throws BeerAlreadyExistsException {
    beerRepository.findByName(name).ifPresent(b -> { throw new BeerAlreadyExistsException(name); });
  }

}
