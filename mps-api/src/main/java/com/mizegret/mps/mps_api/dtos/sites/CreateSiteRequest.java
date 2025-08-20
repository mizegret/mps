package com.mizegret.mps.mps_api.dtos.sites;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;
import org.hibernate.validator.constraints.URL;

@Value
public class CreateSiteRequest {
  @NotBlank
  @Size(max = 200)
  private String name;

  @NotBlank @URL private String url;
}
