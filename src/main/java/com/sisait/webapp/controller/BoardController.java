package com.sisait.webapp.controller;


import com.sisait.webapp.domain.BoardEntity;
import com.sisait.webapp.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    // 게시판 글등록
    private final BoardService service;
    @PostMapping("/boardWrite")
    public String boardWrite(@RequestBody BoardEntity entity, HttpServletRequest request){
        entity.setIp(request.getRemoteAddr());
        System.out.println(entity.toString());
        //insert . -> 등록  save() -> select를 반환
        BoardEntity insertEntity = service.boardWrite(entity);
        return "OK";

    }
}
