package com.sisait.webapp.controller;

// 회원가입, 로그인, 로그아웃, 회원정보수정

import com.sisait.webapp.domain.JoinsEntity;
import com.sisait.webapp.service.JoinsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor // private f// inal 선언된 변수에 객체를 생성해서 대입해준다.
@RequestMapping("/joins")
public class JoinsController {
    private final JoinsService service;
    private Object JoinsEntity;

    //회원가입 구현
    @RequestMapping("/joinsForm")
    public Integer joinsForm(@RequestBody JoinsEntity joinsEntity){
//        System.out.println(joinsEntity.toString());
        return service.createJoins(joinsEntity).getId();


    }

    //로그인 구현
    @PostMapping("/login")
    public String joinsLogin(@RequestBody JoinsEntity joinsEntity) {
        System.out.println(joinsEntity.toString());
        JoinsEntity = service.login(joinsEntity);
        return "test";
    }
}
