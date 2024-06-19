package org.alexm.cloudstorage.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadRequest {
    private MultipartFile file;
}
