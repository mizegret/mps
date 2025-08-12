package com.mizegret.mps.mps_api.entities;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder(toBuilder = true)
public class Product {
  private final UUID id;
  @Setter
  private String name;
  @Setter
  private String description;
  private final OffsetDateTime createdAt;
  @Setter
  private OffsetDateTime updatedAt;
}
