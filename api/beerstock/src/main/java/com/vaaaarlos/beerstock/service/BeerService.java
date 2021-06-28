package com.vaaaarlos.beerstock.service;

import static com.vaaaarlos.beerstock.util.BeerstockUtils.BASIC_MESSAGE;
import static com.vaaaarlos.beerstock.util.BeerstockUtils.Operation.SAVED;

import com.vaaaarlos.beerstock.dto.mapper.BeerMapper;
import com.vaaaarlos.beerstock.dto.request.BeerInsertRequest;
import com.vaaaarlos.beerstock.dto.response.MessageResponse;
import com.vaaaarlos.beerstock.entity.Beer;
import com.vaaaarlos.beerstock.exception.BeerAlreadyExistsException;
import com.vaaaarlos.beerstock.exception.BeerNotFoundException;
import com.vaaaarlos.beerstock.repository.BeerRepository;
import com.vaaaarlos.beerstock.util.BeerstockUtils;

import org.springframework.beans.factory.annotation.Autowired;
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

  public MessageResponse save(BeerInsertRequest beerInsertRequest) {
    verifyIfExists(beerInsertRequest.getName());
    var beerToSave = beerMapper.toModel(beerInsertRequest);
    var savedBeer = beerRepository.save(beerToSave);
    return BeerstockUtils.createMessageResponse(BASIC_MESSAGE, SAVED, savedBeer.getId());

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