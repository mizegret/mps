package com.mizegret.mps.mps_api.mappers;

import com.mizegret.mps.mps_api.dtos.products.CreateProductRequest;
import com.mizegret.mps.mps_api.dtos.products.PatchProductRequest;
import com.mizegret.mps.mps_api.dtos.products.ProductResponse;
import com.mizegret.mps.mps_api.entities.Product;

import lombok.NonNull;

import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductMapper {

  // entity -> dto
  ProductResponse toResponse(@NonNull Product entity);

  List<ProductResponse> toResponseList(@NonNull List<Product> entities);

  // dto -> entity
  @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
  @Mapping(target = "createdAt", expression = "java(java.time.OffsetDateTime.now())")
  @Mapping(target = "updatedAt", expression = "java(java.time.OffsetDateTime.now())")
  Product toEntity(@NonNull CreateProductRequest req);

  @BeanMapping(
      ignoreByDefault = true,
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "name", source = "name")
  @Mapping(target = "description", source = "description")
  void merge(@NonNull @MappingTarget Product target, @NonNull PatchProductRequest patch);

  @AfterMapping
  default void touchUpdatedAt(@NonNull @MappingTarget Product target, @NonNull PatchProductRequest patch) {
    target.setUpdatedAt(java.time.OffsetDateTime.now());
  }
}
