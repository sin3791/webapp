package com.sisait.webapp.controller;


import com.sisait.webapp.domain.BoardEntity;
import com.sisait.webapp.domain.PagingVO;
import com.sisait.webapp.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

//import java.awt.print.Pageable;
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
    public Map<String, Object> boardList(PagingVO vo, @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        //리스트 페이지로 보낼 정보를 담을 컬랙션
        Map<String, Object> map = new HashMap<String, Object>();
        // 페이징, 검색
        //DB의 모든 레코드를 desc선택하여 List<BoardEntity>에 담아 변환

        //총 레코드수를 구하여 vo에 totalRecord에 대입
        vo.setTotalRecord(service.getTotalRecordCount(vo));

        System.out.println("페이지의 검색어 정보 ====>" + vo.toString());
//        List<BoardEntity> list = service.boardAllSelectList();
        List<BoardEntity> list = service.boardPageList(vo); // 검색어 처리,

        map.put("boardList", list); //목록

        map.put("pages", vo);
        return map;
    }
    @GetMapping("/boardView/{id}")
    public BoardEntity boardSelect(@PathVariable("id") int id){

        //조회수 증가
        service.hitCount(id);

        return service.boardSelect(id);
    }
    @GetMapping("/boardEdit/{id}")
    public BoardEntity boardEditSelect(@PathVariable("id") int id){

        return service.boardSelect(id);
    }

    //글 수정 db업데이트
    @PostMapping("/boardEditOk")
    public String boardEditOk(@RequestBody BoardEntity entity){
        System.out.println(entity.toString());
        //기존 글 내용이 있는 레코드를 가져와야함
        BoardEntity selectEntity = service.boardSelect(entity.getId());
        selectEntity.setSubject(entity.getSubject()); //수정한 제목으로 변경
        selectEntity.setContent(entity.getContent()); //수정한 글 내용으로 변경
        //id가 있기 떄문에 save()메소드는 update문을 만들어 구현한다.
        BoardEntity resultEntity = service.boardWrite(selectEntity);


        if(resultEntity != null){
            return "Ok";
        } else{
            return "fail";

        }
    }


    //글 삭제
    @GetMapping("/boardDel/{id}")
    public String boardDel(@PathVariable("id") int id){
        int result = service.boardDelete(id);
        return result+"";
    }
}
