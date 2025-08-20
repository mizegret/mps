package com.mizegret.mps.mps_api.services;

import com.mizegret.mps.mps_api.dtos.sites.CreateSiteRequest;
import com.mizegret.mps.mps_api.dtos.sites.PatchSiteRequest;
import com.mizegret.mps.mps_api.dtos.sites.SiteResponse;
import com.mizegret.mps.mps_api.entities.Site;
import com.mizegret.mps.mps_api.mappers.SiteMapper;
import com.mizegret.mps.mps_api.repositories.SiteRepository;
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
public class SiteServiceImpl implements SiteService {

  private static final String RESOURCE_NAME = "Site";
  private final SiteMapper siteMapper;
  private final SiteRepository siteRepository;

  @Override
  public List<SiteResponse> getAllSites() {
    return siteMapper.toResponseList(siteRepository.findAll());
  }

  @Override
  public SiteResponse getSiteById(@NonNull UUID id) {
    return siteRepository
        .findById(id)
        .map(siteMapper::toResponse)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, id.toString()));
  }

  @Override
  @Transactional
  public SiteResponse createSite(@NonNull CreateSiteRequest req) {
    final Site saved = siteRepository.save(siteMapper.toEntity(req));
    return siteMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public SiteResponse patchSite(@NonNull UUID id, @NonNull PatchSiteRequest req) {
    final Site site =
        siteRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, id.toString()));
    siteMapper.merge(site, req);
    final Site updated = siteRepository.save(site);
    return siteMapper.toResponse(updated);
  }

  @Override
  @Transactional
  public void deleteAllSites() {
    siteRepository.deleteAll();
  }

  @Override
  @Transactional
  public void deleteSiteById(@NonNull UUID id) {
    siteRepository.deleteById(id);
  }
}
