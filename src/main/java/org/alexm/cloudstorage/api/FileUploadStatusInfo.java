package org.alexm.cloudstorage.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileUploadStatusInfo {
    private String uploadId;
    private String filename;
    private FileUploadStatus status;
}
