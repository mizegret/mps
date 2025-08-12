package com.mizegret.mps.mps_api.dtos.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class CreateProductRequest {
  @NotBlank
  @Size(max = 200)
  private String name;

  @Size(max = 1000)
  private String description;
}
