package com.mizegret.mps.mps_api.services;

import com.mizegret.mps.mps_api.dtos.sites.CreateSiteRequest;
import com.mizegret.mps.mps_api.dtos.sites.PatchSiteRequest;
import com.mizegret.mps.mps_api.dtos.sites.SiteResponse;
import java.util.List;
import java.util.UUID;

public interface SiteService {
  public List<SiteResponse> getAllSites();

  public SiteResponse getSiteById(UUID id);

  public SiteResponse createSite(CreateSiteRequest req);

  public SiteResponse patchSite(UUID id, PatchSiteRequest req);

  public void deleteAllSites();

  public void deleteSiteById(UUID id);
}
