package com.mizegret.mps.mps_api.dtos.products;

import com.mizegret.mps.mps_api.validators.AnyNotNull;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
@AnyNotNull
public class PatchProductRequest {
  @Size(min = 1, max = 200)
  @Pattern(regexp = ".*\\S.*", message = "must not be blank")
  private String name;

  @Size(min = 1, max = 1000)
  @Pattern(regexp = ".*\\S.*", message = "must not be blank")
  private String description;
}
