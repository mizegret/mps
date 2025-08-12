package com.mizegret.mps.mps_api.services;

import com.mizegret.mps.mps_api.dtos.products.CreateProductRequest;
import com.mizegret.mps.mps_api.dtos.products.PatchProductRequest;
import com.mizegret.mps.mps_api.dtos.products.ProductResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  @Override
  public List<ProductResponse> getAllProducts() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAllProducts'");
  }

  @Override
  public ProductResponse getProductById(@NonNull UUID id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getProductById'");
  }

  @Override
  public ProductResponse createProduct(@NonNull CreateProductRequest req) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createProduct'");
  }

  @Override
  public ProductResponse patchProduct(@NonNull UUID id, @NonNull PatchProductRequest req) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateProduct'");
  }

  @Override
  public void deleteAllProducts() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteAllProducts'");
  }

  @Override
  public void deleteProductById(@NonNull UUID id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteProductById'");
  }
}
