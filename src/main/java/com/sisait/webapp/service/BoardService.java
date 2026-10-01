package com.sisait.webapp.service;

import com.sisait.webapp.domain.BoardEntity;
import com.sisait.webapp.domain.PagingVO;
import com.sisait.webapp.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

    public List<BoardEntity> boardAllSelectList(PagingVO vo) {

        return repository.findAllByOrderByIdDesc(PageRequest.of(vo.getNowPage() - 1, vo.getOnePageRecord()));
    }

    public int getTotalRecordCount() {
        //select count(id) from board_entity;
        // 검색어가 없을때
        return (int)repository.countIdBy();
    }

    //해당페이지, 검색을 이용한 선택/
    public List<BoardEntity> boardPageList(PagingVO vo) {
        // 1. 컨트롤러 매핑에 @PageableDefault 어노테이션 기술하기

        //                                                                      선택할 페이지
        List<BoardEntity> list = repository.findAllByOrderByIdDesc(PageRequest.of(vo.getNowPage()- 1, vo.getOnePageRecord()));
        return list;
    }
}
