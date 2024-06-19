package org.alexm.cloudstorage.mapper;

import org.alexm.cloudstorage.api.FileUploadStatus;
import org.alexm.cloudstorage.api.FileUploadStatusInfo;
import org.alexm.cloudstorage.model.FileToUpload;

public class FileUploadStatusInfoMapper {
    public static FileUploadStatusInfo fromFileToUpload(FileToUpload fileToUpload) {
        return new FileUploadStatusInfo(
                fileToUpload.getUploadId(),
                fileToUpload.getFilename(),
                FileUploadStatus.valueOf(fileToUpload.getStatus())
        );
    }
}
