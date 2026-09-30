package com.sisait.webapp.repository;

import com.sisait.webapp.domain.BoardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
}
