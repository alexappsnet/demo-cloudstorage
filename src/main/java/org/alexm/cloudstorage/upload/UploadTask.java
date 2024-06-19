package org.alexm.cloudstorage.upload;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexm.cloudstorage.api.FileUploadStatus;
import org.alexm.cloudstorage.model.FileToUploadDTO;
import org.alexm.cloudstorage.model.UserFile;
import org.alexm.cloudstorage.repository.FileToUploadRepository;
import org.alexm.cloudstorage.repository.UserFileRepository;

import java.io.InputStream;

import static java.util.concurrent.TimeUnit.SECONDS;

@AllArgsConstructor()
@Slf4j
public class UploadTask implements Runnable {
    private final FileToUploadDTO file;
    private final InputStream contentStream;
    private final UserFileRepository userFileRepository;
    private final FileToUploadRepository fileToUploadRepository;

    @Override
    public void run() {
        try {
            markAsInProgress();
            upload();
            markAsCompleted();
        } catch (Throwable t) {
            log.error("Exception while uploading", t);
            markAsFailed();
        }
    }

    private void addToUserFiles(String s3Path, long size) {
        UserFile userFile = new UserFile();
        userFile.setUploadId(file.getUploadId());
        userFile.setUserId(file.getUserId());
        userFile.setFilename(file.getFilename());
        userFile.setS3Path(s3Path);
        userFile.setSize(size);
        userFileRepository.save(userFile);
    }

    private void upload() throws Exception {
        var uploadId = file.getUploadId();

        log.info("{}: started", uploadId);
        Thread.sleep(SECONDS.toMillis(10));
        log.info("{}: still going", uploadId);
        Thread.sleep(SECONDS.toMillis(20));
        if ('A' <= uploadId.charAt(0)) {
            log.info("{}: failed", uploadId);
            throw new Exception("Failed");
        }

        // Out of curiosity, checking if we get the 3GB stream here while the
        // heap usage should NOT be too high. Confirmed with Profiler and Postman
        // that the file is uploaded in its full content of 3,221,225,472 bytes
        // while application memory heap usage never went over 120 MB.
        long calculatedSize = 0;
        byte[] buffer = new byte[1000_000];
        int r;
        while ((r = contentStream.read(buffer)) != -1) {
            calculatedSize += r;
        }

        log.info("{}: done successfully. {} bytes", uploadId, calculatedSize);
        addToUserFiles("s3://cloudstorage/" + uploadId, calculatedSize);
    }

    private void markAsFailed() {
        markAs(FileUploadStatus.FAILED);
    }

    private void markAsCompleted() {
        markAs(FileUploadStatus.COMPLETED);
    }

    private void markAsInProgress() {
        markAs(FileUploadStatus.IN_PROGRESS);
    }

    private void markAs(FileUploadStatus status) {
        fileToUploadRepository.updateStatus(file.getUploadId(), status.name());
    }
}
