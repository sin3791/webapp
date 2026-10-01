package com.sisait.webapp.controller;


import com.sisait.webapp.domain.BoardEntity;
import com.sisait.webapp.domain.PagingVO;
import com.sisait.webapp.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/boardList")
    public Map<String, Object> boardList(PagingVO vo){
        //리스트 페이지로 보낼 정보를 담을 컬랙션
        Map<String, Object> map = new HashMap<String, Object>();
        // 페이징, 검색
        //DB의 모든 레코드를 desc선택하여 List<BoardEntity>에 담아 변환

        //총 레코드수를 구하여 vo에 totalRecord에 대입
        vo.setTotalRecord(service.getTotalRecordCount());

        System.out.println("페이지의 검색어 정보 ====>" + vo.toString());
        List<BoardEntity> list = service.boardAllSelectList();
        map.put("boardList", list); //목록

        map.put("pages", vo);
        return map;
    }
}
