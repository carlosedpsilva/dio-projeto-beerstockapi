package com.vaaaarlos.beerstock.utils;

import static com.vaaaarlos.beerstock.enums.BeerType.LAGER;

import com.vaaaarlos.beerstock.dto.request.BeerInsertRequest;
import com.vaaaarlos.beerstock.dto.request.BeerQuantityPatchRequest;
import com.vaaaarlos.beerstock.dto.response.BeerResponse;
import com.vaaaarlos.beerstock.entity.Beer;
import com.vaaaarlos.beerstock.enums.BeerType;

public class BeerUtils {

  private static long ID = 1L;
  private static String NAME = "Brahma";
  private static String BRAND = "Ambev";
  private static int MAX = 50;
  private static int QUANTITY = 10;
  private static BeerType TYPE = LAGER;

  public static Beer createFakeEntity() {
    return Beer.builder()
        .id(ID)
        .name(NAME)
        .brand(BRAND)
        .max(MAX)
        .quantity(QUANTITY)
        .type(TYPE)
        .build();
  }

  public static BeerInsertRequest createFakeBeerInsertRequest() {
    return BeerInsertRequest.builder()
        .name(NAME)
        .brand(BRAND)
        .max(MAX)
        .quantity(QUANTITY)
        .type(TYPE)
        .build();
  }

  public static BeerQuantityPatchRequest createFakeBeerQuantityPatchRequest() {
    return BeerQuantityPatchRequest.builder()
        .quantity(QUANTITY)
        .build();
  }

  public static BeerResponse createFakeBeerResponse() {
    return BeerResponse.builder()
        .id(ID)
        .name(NAME)
        .brand(BRAND)
        .max(MAX)
        .quantity(QUANTITY)
        .type(TYPE)
        .build();
  }

}
