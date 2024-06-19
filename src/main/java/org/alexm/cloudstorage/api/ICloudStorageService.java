package org.alexm.cloudstorage.api;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICloudStorageService {
    List<FileInfo> getFiles(String userId);

    FileUploadStatusInfo getFileUploadStatus(String userId, String uploadId);

    FileUploadStatusInfo uploadFile(String userId, MultipartFile file);
}
