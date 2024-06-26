package org.alexm.cloudstorage.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FileInfo {
    private String uploadId;
    private String filename;
    private long size;
}
