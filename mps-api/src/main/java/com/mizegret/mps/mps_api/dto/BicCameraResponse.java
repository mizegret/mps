package com.mizegret.mps.mps_api.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class BicCameraResponse{
    private final String productName;
    private final String price;
}
