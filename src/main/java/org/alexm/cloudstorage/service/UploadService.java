package org.alexm.cloudstorage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexm.cloudstorage.model.FileToUploadDTO;
import org.alexm.cloudstorage.repository.FileToUploadRepository;
import org.alexm.cloudstorage.repository.UserFileRepository;
import org.alexm.cloudstorage.upload.UploadTask;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadService {
    @Qualifier("uploads")
    private final TaskExecutor taskExecutor;
    private final UserFileRepository userFileRepository;
    private final FileToUploadRepository fileToUploadRepository;

    public void schedule(FileToUploadDTO file, InputStream contentStream) {
        log.info("Scheduling {}, {} bytes", file.getUploadId(), file.getSize());
        UploadTask task = new UploadTask(
                file,
                contentStream,
                userFileRepository,
                fileToUploadRepository);
        taskExecutor.execute(task);
    }
}
