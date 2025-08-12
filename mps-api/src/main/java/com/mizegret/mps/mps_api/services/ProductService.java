package com.mizegret.mps.mps_api.services;

import com.mizegret.mps.mps_api.dtos.products.CreateProductRequest;
import com.mizegret.mps.mps_api.dtos.products.PatchProductRequest;
import com.mizegret.mps.mps_api.dtos.products.ProductResponse;
import java.util.List;
import java.util.UUID;

public interface ProductService {
  public List<ProductResponse> getAllProducts();

  public ProductResponse getProductById(UUID id);

  public ProductResponse createProduct(CreateProductRequest req);

  public ProductResponse patchProduct(UUID id, PatchProductRequest req);

  public void deleteAllProducts();

  public void deleteProductById(UUID id);
}
