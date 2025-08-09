package com.mizegret.mps.mps_api.service;

import org.springframework.lang.NonNull;

import com.mizegret.mps.mps_api.dto.BicCameraRequest;
import com.mizegret.mps.mps_api.dto.BicCameraResponse;

public interface BicCameraService{
    BicCameraResponse scrape(@NonNull BicCameraRequest request);
}
