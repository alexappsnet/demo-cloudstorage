package org.alexm.cloudstorage.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(indexes = {@Index(name="user_id_idx", columnList = "user_id")})
@Data
public class UserFile {
    @Id
    String uploadId;

    String userId;

    @Column(length=2048)
    String filename;

    long size;

    @Column(length=2048)
    String s3Path;
}
