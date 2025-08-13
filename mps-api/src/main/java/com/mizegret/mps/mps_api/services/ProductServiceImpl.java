package com.mizegret.mps.mps_api.services;

import com.mizegret.mps.mps_api.dtos.products.CreateProductRequest;
import com.mizegret.mps.mps_api.dtos.products.PatchProductRequest;
import com.mizegret.mps.mps_api.dtos.products.ProductResponse;
import com.mizegret.mps.mps_api.entities.Product;
import com.mizegret.mps.mps_api.mappers.ProductMapper;
import com.mizegret.mps.mps_api.repositories.ProductJpaRepository;
import com.mizegret.mps.mps_core.exception.ResourceNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

  private static final String RESOURCE_NAME = "Product";
  private final ProductMapper productMapper;
  private final ProductJpaRepository productJpaRepository;

  @Override
  public List<ProductResponse> getAllProducts() {
    return productMapper.toResponseList(productJpaRepository.findAll());
  }

  @Override
  public ProductResponse getProductById(@NonNull UUID id) {
    return productJpaRepository
        .findById(id)
        .map(productMapper::toResponse)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, id.toString()));
  }

  @Override
  @Transactional
  public ProductResponse createProduct(@NonNull CreateProductRequest req) {
    final Product saved = productJpaRepository.save(productMapper.toEntity(req));
    return productMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public ProductResponse patchProduct(@NonNull UUID id, @NonNull PatchProductRequest req) {
    final Product product =
        productJpaRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, id.toString()));
    productMapper.merge(product, req);
    final Product updated = productJpaRepository.save(product);
    return productMapper.toResponse(updated);
  }

  @Override
  @Transactional
  public void deleteAllProducts() {
    productJpaRepository.deleteAll();
  }

  @Override
  @Transactional
  public void deleteProductById(@NonNull UUID id) {
    productJpaRepository.deleteById(id);
  }
}
