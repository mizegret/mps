package com.mizegret.mps.mps_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(
        regexp = "^https?://www\\.biccamera\\.com/bc/item/[0-9]+$",
        flags = {Pattern.Flag.CASE_INSENSITIVE},
        message = "productUrl must be under biccamera.com"
    )
    private String productUrl;
}
