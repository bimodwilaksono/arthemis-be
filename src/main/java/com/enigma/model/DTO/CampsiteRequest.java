package com.enigma.model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CampsiteRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotBlank
    private String province;
    private MultipartFile file;
    private String orderId;
    private String ratingId;
}
