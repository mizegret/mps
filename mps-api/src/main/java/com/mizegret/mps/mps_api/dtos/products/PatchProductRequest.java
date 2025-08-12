package com.mizegret.mps.mps_api.dtos.products;

import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class PatchProductRequest {
  @Size(max = 200)
  private String name;

  @Size(max = 1000)
  private String description;
}
