package com.mizegret.mps.mps_api.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(HealthController.HEALTH_ENDPOINT)
@Slf4j
public class HealthController {

  public static final String HEALTH_ENDPOINT = "/api/v1/health";

  @GetMapping(value = "")
  public ResponseEntity<String> health() {
    log.info("Received request for {}", HEALTH_ENDPOINT);
    final String response = "Hello, MPS API is running!";
    log.info("Returning response: {}", response);
    return ResponseEntity.ok(response);
  }
}
