package com.sisait.webapp.repository;

import com.sisait.webapp.domain.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRepository extends JpaRepository<DataEntity, Integer> {

}
