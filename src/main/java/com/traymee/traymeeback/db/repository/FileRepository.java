package com.traymee.traymeeback.db.repository;

import com.traymee.traymeeback.db.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="filesContent", collectionResourceRel="files")
public interface FileRepository extends JpaRepository<File, Long> {
}
