package com.vaaaarlos.beerstock.dto.request;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.vaaaarlos.beerstock.enums.BeerType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerInsertRequest {

  @NotBlank(message = "Beer name may not be blank.")
  private String name;

  @NotBlank(message = "Beer brand may not be blank.")
  private String brand;

  @Max(500)
  @NotNull(message = "Beer max quantity may not be blank.")
  private Integer max;

  @Max(100)
  @NotNull(message = "Beer quantity may not be blank.")
  private Integer quantity;

  @Enumerated(EnumType.STRING)
  @NotNull(message = "Beer type may not be blank.")
  private BeerType type;

}
