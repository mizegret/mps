package com.mizegret.mps.mps_api.services;

import org.springframework.lang.NonNull;

import com.mizegret.mps.mps_api.dtos.BicCameraRequest;
import com.mizegret.mps.mps_api.dtos.BicCameraResponse;

public interface BicCameraService{
    BicCameraResponse scrape(@NonNull BicCameraRequest request) throws Exception;
}
