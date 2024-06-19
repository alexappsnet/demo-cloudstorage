package org.alexm.cloudstorage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class FileToUpload {
    @Id
    String uploadId;

    @Column(length=2048)
    String filename;

    String userId;

    String status;

    long size;
}
