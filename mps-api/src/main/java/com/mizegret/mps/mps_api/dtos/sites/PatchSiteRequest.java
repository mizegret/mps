package com.mizegret.mps.mps_api.dtos.sites;

import com.mizegret.mps.mps_core.validators.AnyNotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;
import org.hibernate.validator.constraints.URL;

@Value
@AnyNotNull
public class PatchSiteRequest {
  @Size(max = 200)
  private String name;

  @URL private String url;
}
