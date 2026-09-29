package com.sisait.webapp.service;

import com.sisait.webapp.domain.JoinsEntity;
import com.sisait.webapp.repository.JoinsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service        //비즈니스 구현을 위한 클래스, jpa repository를 호출할 클래스
@RequiredArgsConstructor
public class JoinsService {
    // 회원인증과 관련된 DB작업을 할 repository 객체생성
    private final JoinsRepository repository;

    //회원등록
    public JoinsEntity createJoins(JoinsEntity joinsEntity) {
        // @id의 필드가 null이면 insert -> select를 실행한다.
        //          null이 아니면 Update -> select 를 실행한다.
        System.out.println(joinsEntity.toString());
        return repository.save(joinsEntity);
    }

    public JoinsEntity login(JoinsEntity joinsEntity) {
        //로그인
        //select * from joins_entity where userid = ? && password=?
        JoinsEntity entity = repository.findByUseridAndPassword(joinsEntity.getUserid(), joinsEntity.getPassword());
        System.out.println(entity);

        // 로그인 성공: 세션에 필요한 정보기록()

        // 로그인 실패
        return entity;
    }
}