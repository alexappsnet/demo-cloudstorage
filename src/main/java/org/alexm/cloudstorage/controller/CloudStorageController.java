package org.alexm.cloudstorage.controller;

import lombok.RequiredArgsConstructor;
import org.alexm.cloudstorage.api.FileInfo;
import org.alexm.cloudstorage.api.FileUploadStatusInfo;
import org.alexm.cloudstorage.api.ICloudStorageService;
import org.alexm.cloudstorage.model.UploadRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CloudStorageController {
    private final ICloudStorageService service;

    @PostMapping("/upload/{userId}")
    public FileUploadStatusInfo uploadFile(@PathVariable String userId,
                                           @ModelAttribute UploadRequest request) {
        MultipartFile file = request.getFile();
        if (file == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "file is null");
        }
        return service.uploadFile(userId, file);
    }

    @GetMapping("/status/{userId}/{uploadId}")
    public FileUploadStatusInfo fileStatus(@PathVariable String userId,
                                           @PathVariable String uploadId) {
        var statusInfo = service.getFileUploadStatus(userId, uploadId);
        if (statusInfo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "file not found");
        }

        return statusInfo;
    }

    @GetMapping("/list/{userId}")
    public List<FileInfo> listFiles(@PathVariable String userId) {
        return service.getFiles(userId);
    }
}
