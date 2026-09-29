package com.sisait.webapp.controller;

// 회원가입, 로그인, 로그아웃, 회원정보수정

import com.sisait.webapp.domain.JoinsEntity;
import com.sisait.webapp.service.JoinsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor // private final 선언된 변수에 객체를 생성해서 대입해준다.
public class JoinsController {
    private final JoinsService service;

    //회원가입 구현
    public Integer joinsForm(@RequestBody JoinsEntity joinsEntity){
        System.out.println(joinsEntity.toString());
        return 1;
    }
}
