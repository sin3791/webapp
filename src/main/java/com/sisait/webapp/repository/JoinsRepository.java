package com.sisait.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sisait.webapp.domain.JoinsEntity; // 엔티티 클래스 임포트

// JpaRepository<다룰 엔티티 클래스, 기본키(PK)의 타입>
public interface JoinsRepository extends JpaRepository<JoinsEntity, Integer> {
}