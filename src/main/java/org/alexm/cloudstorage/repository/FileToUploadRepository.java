package org.alexm.cloudstorage.repository;

import org.alexm.cloudstorage.model.FileToUpload;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface FileToUploadRepository extends CrudRepository<FileToUpload, String> {
    @Transactional
    @Modifying
    @Query("""
        UPDATE FileToUpload ftu
          SET ftu.status = ?2
          WHERE ftu.uploadId = ?1
    """)
    void updateStatus(String uploadId, String status);
}
