package com.vaaaarlos.beerstock.cucumber.context.controller;

import com.vaaaarlos.beerstock.controller.BeerController;
import com.vaaaarlos.beerstock.service.BeerService;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@Configuration
public class BeerControllerContextConfiguration {

  @Bean("testMockMvc")
  public MockMvc mockMvc() {
    var beerController = new BeerController(beerService);
    return MockMvcBuilders.standaloneSetup(beerController).build();
  }

  @MockBean(name = "mockedBeerService")
  public BeerService beerService;

}
