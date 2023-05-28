package com.enigma.model.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileUploadRequest {
    MultipartFile ProfilePic;
}
