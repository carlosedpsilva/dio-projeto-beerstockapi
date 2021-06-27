package com.vaaaarlos.beerstock.cucumber.context.service;

import com.vaaaarlos.beerstock.dto.mapper.BeerMapper;
import com.vaaaarlos.beerstock.repository.BeerRepository;
import com.vaaaarlos.beerstock.service.BeerService;

import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeerServiceContextConfiguration {

  @Bean("testBeerService")
  public BeerService testBeerServiceBean() {
    return new BeerService(beerRepository, beerMapper);
  }

  @MockBean(name = "mockedBeerRepository")
  private BeerRepository beerRepository;

  @Spy
  private BeerMapper beerMapper = Mappers.getMapper(BeerMapper.class);

}
