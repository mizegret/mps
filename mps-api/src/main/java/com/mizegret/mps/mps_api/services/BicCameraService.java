package com.mizegret.mps.mps_api.services;

import com.mizegret.mps.mps_api.dtos.BicCameraRequest;
import com.mizegret.mps.mps_api.dtos.BicCameraResponse;
import lombok.NonNull;

public interface BicCameraService {
  BicCameraResponse scrape(@NonNull BicCameraRequest request) throws Exception;
}
