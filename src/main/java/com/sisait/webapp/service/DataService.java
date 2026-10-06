package com.sisait.webapp.service;

import com.sisait.webapp.domain.DataEntity;
import com.sisait.webapp.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service

@RequiredArgsConstructor
public class DataService {
    private final DataRepository repository;

    public DataEntity dataInsert(DataEntity entity) {
        return repository.save(entity);
    }
}
