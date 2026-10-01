package com.sisait.webapp.service;

import com.sisait.webapp.domain.BoardEntity;
import com.sisait.webapp.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor // private final로 선언된 변수의 객체 생성

public class BoardService {
    private final BoardRepository repository;

    public BoardEntity boardWrite(BoardEntity entity) {
        return repository.save(entity);
    }

    public List<BoardEntity> boardAllSelectList() {

        return repository.findAllByOrderByIdDesc();
    }
}
