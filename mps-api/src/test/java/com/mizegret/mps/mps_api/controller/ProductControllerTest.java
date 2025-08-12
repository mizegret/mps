package com.mizegret.mps.mps_api.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mizegret.mps.mps_api.controllers.ProductController;
import com.mizegret.mps.mps_api.dtos.products.CreateProductRequest;
import com.mizegret.mps.mps_api.dtos.products.PatchProductRequest;
import com.mizegret.mps.mps_api.dtos.products.ProductResponse;
import com.mizegret.mps.mps_api.services.ProductService;
import com.mizegret.mps.mps_core.exception.ResourceNotFoundException;
import com.mizegret.mps.mps_core.handler.GlobalExceptionHandler;
import jakarta.validation.ConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Import(GlobalExceptionHandler.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

  @Autowired private MockMvc mockMvc;

  @SuppressWarnings("removal")
  @MockBean
  private ProductService productService;

  @Autowired private ObjectMapper objectMapper;

  private ProductResponse createTestProductResponse() {
    return new ProductResponse(
        UUID.randomUUID(),
        "Test Product",
        "Test Description",
        OffsetDateTime.now(),
        OffsetDateTime.now());
  }

  @Test
  void getAllProducts_shouldReturnOkAndEmptyList_whenNoProductsExist() throws Exception {
    when(productService.getAllProducts()).thenReturn(Collections.emptyList());

    mockMvc
        .perform(get(ProductController.BASE_ENDPOINT))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  void getAllProducts_shouldReturnOkAndProductList_whenProductsExist() throws Exception {
    ProductResponse product = createTestProductResponse();
    List<ProductResponse> products = Collections.singletonList(product);

    when(productService.getAllProducts()).thenReturn(products);

    mockMvc
        .perform(get(ProductController.BASE_ENDPOINT))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id").value(product.getId().toString()))
        .andExpect(jsonPath("$[0].name").value(product.getName()))
        .andExpect(jsonPath("$[0].description").value(product.getDescription()));
  }

  @Test
  void getProductById_shouldReturnOkAndProduct_whenProductExists() throws Exception {
    ProductResponse product = createTestProductResponse();
    when(productService.getProductById(product.getId())).thenReturn(product);

    mockMvc
        .perform(get(ProductController.BASE_ENDPOINT.concat("/{id}"), product.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(product.getId().toString()))
        .andExpect(jsonPath("$.name").value(product.getName()))
        .andExpect(jsonPath("$.description").value(product.getDescription()));
  }

  @Test
  void getProductById_shouldReturnBadRequest_whenIdIsNotUuid() throws Exception {
    mockMvc.perform(get(ProductController.BASE_ENDPOINT.concat("/{id}"), "1")).andExpect(status().isBadRequest());
  }

  @Test
  void getProductById_shouldReturnNotFound_whenProductDoesNotExist() throws Exception {
    UUID productId = UUID.randomUUID();
    when(productService.getProductById(productId))
        .thenThrow(new ResourceNotFoundException("Product not found"));

    mockMvc.perform(get(ProductController.BASE_ENDPOINT.concat("/{id}"), productId)).andExpect(status().isNotFound());
  }

  @Test
  void createProduct_shouldReturnCreatedAndProduct_whenRequestIsValid() throws Exception {
    CreateProductRequest request = new CreateProductRequest("New Product", "New Description");
    ProductResponse response =
        new ProductResponse(
            UUID.randomUUID(),
            request.getName(),
            request.getDescription(),
            OffsetDateTime.now(),
            OffsetDateTime.now());

    when(productService.createProduct(any(CreateProductRequest.class))).thenReturn(response);

    mockMvc
        .perform(
            post(ProductController.BASE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(
            header().string("Location", "http://localhost".concat(ProductController.BASE_ENDPOINT).concat("/") + response.getId()))
        .andExpect(jsonPath("$.id").value(response.getId().toString()))
        .andExpect(jsonPath("$.name").value(response.getName()));
  }

  @Test
  void createProduct_shouldReturnBadRequest_whenNameIsNull() throws Exception {
    CreateProductRequest request = new CreateProductRequest(null, "New Description");

    mockMvc
        .perform(
            post(ProductController.BASE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void patchProduct_shouldReturnOkAndPatchedProduct_whenRequestIsValid() throws Exception {
    UUID productId = UUID.randomUUID();
    PatchProductRequest request = new PatchProductRequest("Patched Name", null);
    ProductResponse response =
        new ProductResponse(
            productId,
            request.getName(),
            "Original Description",
            OffsetDateTime.now(),
            OffsetDateTime.now());

    when(productService.patchProduct(eq(productId), any(PatchProductRequest.class)))
        .thenReturn(response);

    mockMvc
        .perform(
            patch(ProductController.BASE_ENDPOINT.concat("/{id}"), productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(response.getId().toString()))
        .andExpect(jsonPath("$.name").value(response.getName()));
  }

  @Test
  void patchProduct_shouldReturnNotFound_whenProductDoesNotExist() throws Exception {
    UUID productId = UUID.randomUUID();
    when(productService.patchProduct(eq(productId), any(PatchProductRequest.class)))
        .thenThrow(new ResourceNotFoundException("Product not found"));

    mockMvc
        .perform(
            patch(ProductController.BASE_ENDPOINT.concat("/{id}"), productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        new PatchProductRequest("New Name", "New Description"))))
        .andExpect(status().isNotFound());
  }

  @Test
  void patchProduct_shouldReturnBadRequest_whenRequestIsInvalid() throws Exception {
    UUID productId = UUID.randomUUID();
    when(productService.patchProduct(eq(productId), any(PatchProductRequest.class)))
        .thenThrow(new ConstraintViolationException("Invalid request", null));

    mockMvc
        .perform(
            patch(ProductController.BASE_ENDPOINT.concat("/{id}"), productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PatchProductRequest("", ""))))
        .andExpect(status().isBadRequest());
  }

  @Test
  void patchProduct_shouldReturnBadRequest_whenBodyIsEmpty() throws Exception {
    UUID id = UUID.randomUUID();

    mockMvc
        .perform(
            patch(ProductController.BASE_ENDPOINT.concat("/{id}"), id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
        .andExpect(status().isBadRequest());

    verifyNoInteractions(productService);
  }

  @Test
  void deleteAllProducts_shouldReturnNoContent() throws Exception {
    doNothing().when(productService).deleteAllProducts();

    mockMvc.perform(delete(ProductController.BASE_ENDPOINT)).andExpect(status().isNoContent());

    verify(productService, times(1)).deleteAllProducts();
  }

  @Test
  void deleteProductById_shouldReturnNoContent() throws Exception {
    UUID productId = UUID.randomUUID();
    doNothing().when(productService).deleteProductById(productId);

    mockMvc.perform(delete(ProductController.BASE_ENDPOINT.concat("/{id}"), productId)).andExpect(status().isNoContent());

    verify(productService, times(1)).deleteProductById(productId);
  }
}
