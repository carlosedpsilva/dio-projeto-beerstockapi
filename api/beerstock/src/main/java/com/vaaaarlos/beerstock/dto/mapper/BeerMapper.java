package com.vaaaarlos.beerstock.dto.mapper;

import com.vaaaarlos.beerstock.dto.request.BeerInsertRequest;
import com.vaaaarlos.beerstock.dto.response.BeerResponse;
import com.vaaaarlos.beerstock.entity.Beer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BeerMapper {

  /*
   * Request
   */

  @Mapping(target = "id", ignore = true)
  Beer toModel(BeerInsertRequest beerInsertRequest);

  /*
   * Response
   */

  BeerResponse toBeerResponse(Beer model);

}
