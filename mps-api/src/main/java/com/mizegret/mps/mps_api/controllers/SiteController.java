package com.mizegret.mps.mps_api.controllers;

import com.mizegret.mps.mps_api.dtos.sites.CreateSiteRequest;
import com.mizegret.mps.mps_api.dtos.sites.PatchSiteRequest;
import com.mizegret.mps.mps_api.dtos.sites.SiteResponse;
import com.mizegret.mps.mps_api.services.SiteService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping(SiteController.BASE_ENDPOINT)
public class SiteController {

  public static final String BASE_ENDPOINT = "/api/v1/sites";
  private final SiteService siteService;

  @GetMapping
  public ResponseEntity<List<SiteResponse>> getAllSites() {
    return ResponseEntity.ok(siteService.getAllSites());
  }

  @GetMapping("/{id}")
  public ResponseEntity<SiteResponse> getSiteById(@PathVariable UUID id) {
    return ResponseEntity.ok(siteService.getSiteById(id));
  }

  @PostMapping
  public ResponseEntity<SiteResponse> createSite(@Valid @RequestBody CreateSiteRequest req) {
    final SiteResponse res = siteService.createSite(req);
    final URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(res.getId())
            .toUri();
    return ResponseEntity.created(location).body(res);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<SiteResponse> patchSite(
      @PathVariable UUID id, @Valid @RequestBody PatchSiteRequest req) {
    return ResponseEntity.ok(siteService.patchSite(id, req));
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteAllSites() {
    siteService.deleteAllSites();
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSiteById(@PathVariable UUID id) {
    siteService.deleteSiteById(id);
    return ResponseEntity.noContent().build();
  }
}
