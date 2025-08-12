package com.mizegret.mps.mps_api.services;

import com.mizegret.mps.mps_api.dtos.products.CreateProductRequest;
import com.mizegret.mps.mps_api.dtos.products.PatchProductRequest;
import com.mizegret.mps.mps_api.dtos.products.ProductResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.lang.NonNull;

public interface ProductService {
  public List<ProductResponse> getAllProducts();

  public ProductResponse getProductById(@NonNull UUID id);

  public ProductResponse createProduct(@NonNull CreateProductRequest req);

  public ProductResponse patchProduct(@NonNull UUID id, @NonNull PatchProductRequest req);

  public void deleteAllProducts();

  public void deleteProductById(@NonNull UUID id);
}
