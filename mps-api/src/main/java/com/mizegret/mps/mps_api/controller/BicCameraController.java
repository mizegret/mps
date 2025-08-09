package com.mizegret.mps.mps_api.controller;

import com.mizegret.mps.mps_api.dto.BicCameraRequest;
import com.mizegret.mps.mps_api.dto.BicCameraResponse;
import com.mizegret.mps.mps_api.service.BicCameraService;
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
public class BicCameraController{

    private final BicCameraService scrapeService;

    @PostMapping(value = "")
    public ResponseEntity<BicCameraResponse> scrapeProduct(@Valid @RequestBody BicCameraRequest request){
        BicCameraResponse response = scrapeService.scrape(request);
        return ResponseEntity.ok(response);
    }
}
