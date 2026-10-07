package com.sisait.webapp.repository;

import com.sisait.webapp.domain.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {

}
