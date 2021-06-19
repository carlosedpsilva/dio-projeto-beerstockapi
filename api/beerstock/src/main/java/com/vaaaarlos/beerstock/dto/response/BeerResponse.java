package com.vaaaarlos.beerstock.dto.response;

import com.vaaaarlos.beerstock.enums.BeerType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerResponse {

  private Long id;
  private String name;
  private String brand;
  private Integer max;
  private Integer quantity;
  private BeerType type;

}
