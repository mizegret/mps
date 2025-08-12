package com.mizegret.mps.mps_api.repositories;

import com.mizegret.mps.mps_api.entities.Product;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;

public interface ProductRepository {
  List<Product> findAll();

  Optional<Product> findById(@NonNull UUID id);

  Product save(@NonNull Product product);

  boolean existsById(@NonNull UUID id);

  void deleteById(@NonNull UUID id);

  void deleteAll();
}
