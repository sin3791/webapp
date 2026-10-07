package com.sisait.webapp.service;

import com.sisait.webapp.domain.DataEntity;
import com.sisait.webapp.domain.FileEntity;
import com.sisait.webapp.repository.DataRepository;
import com.sisait.webapp.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

@RequiredArgsConstructor
public class DataService {
    private final DataRepository repository;
    private final FileRepository fileRepository;

    public DataEntity dataInsert(DataEntity entity) {
        return repository.save(entity);
    }

    public int fileListInsert(List<FileEntity> fileList) {

        int cnt = 0;
        for(FileEntity fEntity : fileList){
            FileEntity resultEntity = fileRepository.save(fEntity);
            if(resultEntity.getId()>0){
                cnt++;
            }


        }
        return cnt;
    }


}
