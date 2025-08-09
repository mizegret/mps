package com.mizegret.mps.mps_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BicCameraRequest{
    @NotBlank(message = "productUrl must not be blank")
    @URL(message = "productUrl must be a valid URL")
    private String productUrl;
}
