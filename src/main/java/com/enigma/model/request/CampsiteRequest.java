package com.enigma.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class CampsiteRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotBlank
    private String province;
    @NotNull
    private BigDecimal price;

    private MultipartFile file;
}
