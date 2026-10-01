package com.sisait.webapp.service;

import com.sisait.webapp.domain.BoardEntity;
import com.sisait.webapp.domain.JoinsEntity;
import com.sisait.webapp.domain.PagingVO;
import com.sisait.webapp.repository.BoardRepository;
import com.sisait.webapp.repository.JoinsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Service
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor // private final로 선언된 변수의 객체 생성

public class BoardService {
    private final BoardRepository repository;
    private final JoinsRepository joinsRepository;
    public BoardEntity boardWrite(BoardEntity entity) {
        return repository.save(entity);
    }

    public List<BoardEntity> boardAllSelectList(PagingVO vo) {

        return repository.findAllByOrderByIdDesc(PageRequest.of(vo.getNowPage() - 1, vo.getOnePageRecord()));
    }

    public int getTotalRecordCount(PagingVO vo) {
        //select count(id) from board_entity;
        // 검색어가 없을때
        if(vo.getSearchWord()==null || vo.getSearchWord().equals("")){
            return (int)repository.countIdBy();

        }else{//검색어가 있을때 :제목, 글내용, 작성자검색
            if (vo.getSearchKey().equals("subject")) {
                return repository.countIdBySubjectContaining(vo.getSearchWord());

            }else if(vo.getSearchKey().equals("content")){
                return repository.countIdByContentContaining(vo.getSearchWord());
            }else{
                return repository.countByJoinsEntity_IdIn(joins_idList(vo.getSearchWord()));
            }

        }
    }

    public List<Integer> joins_idList(String username){
        List<JoinsEntity> list = joinsRepository.findByUsername(username);
        List<Integer> idList = new ArrayList<Integer>();
        for(JoinsEntity e: list){
            idList.add(e.getId());
        }
        return idList;
    }

    //해당페이지, 검색을 이용한 선택/
    public List<BoardEntity> boardPageList(PagingVO vo) {
        // 1. 컨트롤러 매핑에 @PageableDefault 어노테이션 기술하기

        //                                                                      선택할 페이지
        List<BoardEntity> list = repository.findAllByOrderByIdDesc(PageRequest.of(vo.getNowPage()- 1, vo.getOnePageRecord()));
        return list;
    }
}
