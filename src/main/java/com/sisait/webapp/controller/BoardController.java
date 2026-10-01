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
    public String boardWrite(@RequestBody BoardEntity entity, HttpServletRequest request) {
        entity.setIp(request.getRemoteAddr());
        entity.setHit(0);
//        System.out.println("insert 전" + entity.toString());
        //insert . -> 등록  save() -> select를 반환
        BoardEntity insertEntity = service.boardWrite(entity);
//        System.out.println("insert 후"+ insertEntity);

        if (insertEntity.getIp() == null) { //insert안된경우
            return "Fail";
        } else {
            return "OK"; // insert된 경우
        }

    }
}
