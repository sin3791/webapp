package com.sisait.webapp.repository;

import com.sisait.webapp.domain.BoardEntity;
import com.sisait.webapp.domain.JoinsEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    //모든 레코드 선택
    List<BoardEntity> findAllByOrderByIdDesc();
    List<BoardEntity> findAllByOrderByIdDesc(PageRequest pageRequest);


    //검색어가 없을때 총레코드 수
    int countIdBy();

    int countIdBySubjectContaining(String searchWord);

    int countIdByContentContaining(String searchWord);

//    List<JoinsEntity> findByUsername(String username);

//    int countByIdIn(List<Integer> integers);


    int countByJoinsEntity_IdIn(List<Integer> integers);
}
