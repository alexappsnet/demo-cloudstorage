package org.alexm.cloudstorage.mapper;

import org.alexm.cloudstorage.api.FileInfo;
import org.alexm.cloudstorage.model.UserFile;

public class FileInfoMapper {
    public static FileInfo fromUserFile(UserFile fileData) {
        return new FileInfo(
                fileData.getUploadId(),
                fileData.getFilename(),
                fileData.getSize()
        );
    }
}
