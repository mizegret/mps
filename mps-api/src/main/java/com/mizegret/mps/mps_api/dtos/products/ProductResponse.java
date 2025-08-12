package com.mizegret.mps.mps_api.dtos.products;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.NonNull;
import lombok.Value;

@Value
public class ProductResponse {
  @NonNull
  private UUID id;
  @NonNull
  private String name;
  private String description;
  @NonNull
  private OffsetDateTime createdAt;
  @NonNull
  private OffsetDateTime updatedAt;
}
