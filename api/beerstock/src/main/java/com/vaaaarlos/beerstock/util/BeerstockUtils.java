package com.vaaaarlos.beerstock.util;

import com.vaaaarlos.beerstock.dto.response.MessageResponse;

public class BeerstockUtils {

  public static final String BASIC_MESSAGE = "%s Beer with ID %d";

  private BeerstockUtils() { }

  public static MessageResponse createMessageResponse(String message, Object... args) {
    return MessageResponse.builder().message(String.format(message, args)).build();
  }

  public enum Operation {
    DELETED, SAVED, UPDATED;

    @Override
    public String toString() {
      return toString(false);
    }

    public String toString(boolean lower) {
      return lower ? name().toLowerCase() : name().charAt(0) + name().substring(1).toLowerCase();
    }
  }
}
