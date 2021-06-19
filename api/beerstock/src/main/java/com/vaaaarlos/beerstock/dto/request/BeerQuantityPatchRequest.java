package com.vaaaarlos.beerstock.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerQuantityPatchRequest {

  @Max(100)
  @NotNull(message = "Beer quantity may not be blank.")
  private Integer quantity;

}
