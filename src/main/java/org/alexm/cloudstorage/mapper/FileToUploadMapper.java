package org.alexm.cloudstorage.mapper;

import org.alexm.cloudstorage.model.FileToUpload;
import org.alexm.cloudstorage.model.FileToUploadDTO;

public class FileToUploadMapper {
    public static FileToUploadDTO fromFileToUpload(FileToUpload file) {
        return new FileToUploadDTO(
                file.getUploadId(),
                file.getFilename(),
                file.getUserId(),
                file.getStatus(),
                file.getSize()
        );
    }
}
