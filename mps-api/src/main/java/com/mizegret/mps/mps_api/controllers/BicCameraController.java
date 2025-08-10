package com.mizegret.mps.mps_api.controllers;

import com.mizegret.mps.mps_api.dtos.BicCameraRequest;
import com.mizegret.mps.mps_api.dtos.BicCameraResponse;
import com.mizegret.mps.mps_api.services.BicCameraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/biccamera")
@RequiredArgsConstructor
public class BicCameraController {

  private final BicCameraService bicCameraService;

  @PostMapping(value = "")
  public ResponseEntity<BicCameraResponse> scrapeProduct(
      @Valid @RequestBody BicCameraRequest request) throws Exception {
    BicCameraResponse response = bicCameraService.scrape(request);
    return ResponseEntity.ok(response);
  }
}
