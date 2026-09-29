package com.sisait.webapp.service;


import com.sisait.webapp.repository.JoinsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service        //비즈니스 구현을 위한 클래스, jpa repository를 호출할 클래스
@RequiredArgsConstructor
public class JoinsService {
    // 회원인증과 관련된 DB작업을 할 repository 객체생성
    private final JoinsRepository repository;

}
