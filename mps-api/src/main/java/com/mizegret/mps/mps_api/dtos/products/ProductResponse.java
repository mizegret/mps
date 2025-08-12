package com.mizegret.mps.mps_api.dtos.products;

import java.util.UUID;
import lombok.Value;

@Value
public class ProductResponse {
  private UUID id;
  private String name;
  private String description;
}
