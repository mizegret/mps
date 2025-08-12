package com.mizegret.mps.mps_api.controllers;

import com.mizegret.mps.mps_api.dtos.products.CreateProductRequest;
import com.mizegret.mps.mps_api.dtos.products.PatchProductRequest;
import com.mizegret.mps.mps_api.dtos.products.ProductResponse;
import com.mizegret.mps.mps_api.services.ProductService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Validated
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public ResponseEntity<List<ProductResponse>> getAllProducts() {
    return ResponseEntity.ok(productService.getAllProducts());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID id) {
    return ResponseEntity.ok(productService.getProductById(id));
  }

  @PostMapping
  public ResponseEntity<ProductResponse> createProduct(
      @Valid @RequestBody CreateProductRequest req) {
    final ProductResponse res = productService.createProduct(req);
    final URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(res.getId())
            .toUri();
    return ResponseEntity.created(location).body(res);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ProductResponse> patchProduct(
      @PathVariable UUID id, @Valid @RequestBody PatchProductRequest req) {
    return ResponseEntity.ok(productService.patchProduct(id, req));
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteAllProducts() {
    productService.deleteAllProducts();
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProductById(@PathVariable UUID id) {
    productService.deleteProductById(id);
    return ResponseEntity.noContent().build();
  }
}
