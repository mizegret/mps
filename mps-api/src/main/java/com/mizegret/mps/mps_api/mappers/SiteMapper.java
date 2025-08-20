package com.mizegret.mps.mps_api.mappers;

import com.mizegret.mps.mps_api.dtos.sites.CreateSiteRequest;
import com.mizegret.mps.mps_api.dtos.sites.PatchSiteRequest;
import com.mizegret.mps.mps_api.dtos.sites.SiteResponse;
import com.mizegret.mps.mps_api.entities.Site;
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
public interface SiteMapper {

  // entity -> dto
  SiteResponse toResponse(@NonNull Site entity);

  List<SiteResponse> toResponseList(@NonNull List<Site> entities);

  // dto -> entity
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Site toEntity(@NonNull CreateSiteRequest req);

  @Mapping(target = "id", ignore = true)
  @Mapping(
      target = "name",
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(
      target = "url",
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  void merge(@MappingTarget Site entity, @NonNull PatchSiteRequest req);
}
