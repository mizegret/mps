package com.mizegret.mps.mps_api.repositories;

import com.mizegret.mps.mps_api.entities.Product;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class InMemoryProductRepository implements ProductRepository {

  private final ConcurrentHashMap<UUID, Product> store = new ConcurrentHashMap<>();

  @Override
  public List<Product> findAll() {
    return List.copyOf(store.values());
  }

  @Override
  public Optional<Product> findById(@NonNull UUID id) {
    return Optional.ofNullable(store.get(id));
  }

  @Override
  public Product save(@NonNull Product product) {
    store.put(product.getId(), product);
    return product;
  }

  @Override
  public boolean existsById(@NonNull UUID id) {
    return store.containsKey(id);
  }

  @Override
  public void deleteById(@NonNull UUID id) {
    store.remove(id);
  }

  @Override
  public void deleteAll() {
    store.clear();
  }
}
