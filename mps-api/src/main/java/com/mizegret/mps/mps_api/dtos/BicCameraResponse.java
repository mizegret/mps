package com.mizegret.mps.mps_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class BicCameraResponse {
  private final String productName;
  private final int price;
}
