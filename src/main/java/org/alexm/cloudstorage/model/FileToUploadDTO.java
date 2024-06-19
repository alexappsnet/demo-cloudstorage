package org.alexm.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileToUploadDTO {
    String uploadId;
    String filename;
    String userId;
    String status;
    long size;
}
