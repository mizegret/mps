package com.mizegret.mps.mps_api.mappers;

import com.mizegret.mps.mps_api.dtos.products.CreateProductRequest;
import com.mizegret.mps.mps_api.dtos.products.PatchProductRequest;
import com.mizegret.mps.mps_api.dtos.products.ProductResponse;
import com.mizegret.mps.mps_api.entities.Product;
import java.util.List;
import lombok.NonNull;
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
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Product toEntity(@NonNull CreateProductRequest req);

  @Mapping(target = "id", ignore = true)
  @Mapping(
      target = "name",
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  void merge(@MappingTarget Product entity, @NonNull PatchProductRequest req);
}
