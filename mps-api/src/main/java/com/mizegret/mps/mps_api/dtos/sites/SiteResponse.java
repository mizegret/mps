package com.mizegret.mps.mps_api.dtos.sites;

import java.time.Instant;
import java.util.UUID;
import lombok.NonNull;
import lombok.Value;

@Value
public class SiteResponse {
  @NonNull private UUID id;
  @NonNull private String name;
  @NonNull private String url;
  @NonNull private Instant createdAt;
  @NonNull private Instant updatedAt;
}
