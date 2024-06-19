package org.alexm.cloudstorage.repository;

import org.alexm.cloudstorage.model.UserFile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserFileRepository extends CrudRepository<UserFile, String> {
    List<UserFile> findByUserId(String userId);
}
