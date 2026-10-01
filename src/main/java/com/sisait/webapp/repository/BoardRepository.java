package com.sisait.webapp.repository;

import com.sisait.webapp.domain.BoardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    //모든 레코드 선택
    List<BoardEntity> findAllByOrderByIdDesc();
}
