package org.alexm.cloudstorage.service;

import lombok.RequiredArgsConstructor;
import org.alexm.cloudstorage.api.FileInfo;
import org.alexm.cloudstorage.api.FileUploadStatus;
import org.alexm.cloudstorage.api.FileUploadStatusInfo;
import org.alexm.cloudstorage.api.ICloudStorageService;
import org.alexm.cloudstorage.mapper.FileInfoMapper;
import org.alexm.cloudstorage.mapper.FileToUploadMapper;
import org.alexm.cloudstorage.mapper.FileUploadStatusInfoMapper;
import org.alexm.cloudstorage.model.FileToUpload;
import org.alexm.cloudstorage.repository.FileToUploadRepository;
import org.alexm.cloudstorage.repository.UserFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CloudStorageService implements ICloudStorageService {
    private final UserFileRepository userFileRepository;
    private final FileToUploadRepository fileToUploadRepository;
    private final UploadService uploadService;

    @Override
    public List<FileInfo> getFiles(String userId) {
        return userFileRepository.findByUserId(userId)
                .stream()
                .map(FileInfoMapper::fromUserFile)
                .collect(Collectors.toList());
    }

    @Override
    public FileUploadStatusInfo getFileUploadStatus(String userId, String uploadId) {
        FileToUpload fileToUpload = fileToUploadRepository.findById(uploadId).orElse(null);
        if (fileToUpload == null || !Objects.equals(userId, fileToUpload.getUserId())) {
            return null;
        }

        return new FileUploadStatusInfo(
                uploadId,
                fileToUpload.getFilename(),
                FileUploadStatus.valueOf(fileToUpload.getStatus())
        );
    }

    @Override
    public FileUploadStatusInfo uploadFile(String userId, MultipartFile multipartFile) {
        var uploadId = UUID.randomUUID().toString();
        var filename = multipartFile.getOriginalFilename();

        FileToUpload file = new FileToUpload();
        file.setUploadId(uploadId);
        file.setFilename(filename);
        file.setUserId(userId);
        file.setSize(multipartFile.getSize());
        file.setStatus(FileUploadStatus.PENDING.name());

        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            file.setStatus(FileUploadStatus.FAILED.name());
        }
        fileToUploadRepository.save(file);

        if (inputStream != null) {
            uploadService.schedule(FileToUploadMapper.fromFileToUpload(file), inputStream);
        }

        return FileUploadStatusInfoMapper.fromFileToUpload(file);
    }
}
