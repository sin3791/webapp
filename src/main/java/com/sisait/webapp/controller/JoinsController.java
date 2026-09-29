package com.sisait.webapp.controller;

// 회원가입, 로그인, 로그아웃, 회원정보수정

import com.sisait.webapp.domain.JoinsEntity;
import com.sisait.webapp.service.JoinsService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Join;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor // private f// inal 선언된 변수에 객체를 생성해서 대입해준다.
@RequestMapping("/joins")
public class JoinsController {
    private final JoinsService service;
//    private Object JoinsEntity;
//    private JoinsEntity entity;
    //회원가입 구현
    @RequestMapping("/joinsForm")
    public Integer joinsForm(@RequestBody JoinsEntity joinsEntity){
//        System.out.println(joinsEntity.toString());
        return service.createJoins(joinsEntity).getId();


    }

    //로그인 구현
    @PostMapping("/login")
    public JoinsEntity joinsLogin(@RequestBody JoinsEntity joinsEntity, HttpSession session) {

        System.out.println(session.getId());
        JoinsEntity entity = service.login(joinsEntity);
        System.out.println(entity.toString());
        if (entity != null){
            session.setAttribute("Login", entity.getId());
            session.setAttribute("LogId", entity.getUserid());
            session.setAttribute("LogName", entity.getUsername());
            session.setAttribute("LogStatus", "Y");


            entity.setPassword("");
            entity.setTel("");
            entity.setEmail("");
            entity.setWritedate("");

        } else{        // 로그인 실패
            session.setAttribute("LogStatus", "N");
        }

        return entity;
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        //session 객체 자체를 제거한다 --- 새로운 세션이 자동으로 할당된다.
        session.invalidate();
        return "OK";
    }
}
