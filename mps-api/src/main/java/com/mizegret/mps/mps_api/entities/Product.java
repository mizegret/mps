package com.mizegret.mps.mps_api.entities;

import com.mizegret.mps.mps_core.entities.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(schema = "reference", name = "product")
public class Product extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(nullable = false, updatable = false)
  private UUID id;

  @Setter
  @NotBlank
  @Column(nullable = false, columnDefinition = "text")
  private String name;

  @Setter
  @Column(columnDefinition = "text")
  private String description;
}
