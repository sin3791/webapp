package com.sisait.webapp.controller;
import com.sisait.webapp.dto.ProductDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody; // 👈 이 import가 꼭 있어야 합니다!

import java.util.ArrayList;
import java.util.List; // 👈 이 import를 추가해 주세요!
@CrossOrigin(origins = "*")
@Controller
public class FetchController {

    @RequestMapping(value="/fetchTest", method = RequestMethod.GET)
    @ResponseBody
    public ProductDTO fetchTest(HttpServletRequest request){
        String title = request.getParameter("title");
        String content = request.getParameter( "content");
        int hit = Integer.parseInt(request.getParameter("hit"));

        System.out.println("title: " + title);
        System.out.println("content: " + content);
        System.out.println("hit: " + hit);

        // 2. ProductDTO 생성자 인자 전달 문법 수정
        return new ProductDTO("0000", "노트북", "화이트", 20000);
    }

    @RequestMapping(value="/fetchPostTest", method = RequestMethod.POST)
    @ResponseBody
    public List<ProductDTO> fetchPostTest(ProductDTO dto){
        System.out.println(dto.toString());
        List<ProductDTO> list = new ArrayList<ProductDTO>();
        list.add(new ProductDTO("1111", "사과", "레드", 1500));
        list.add(new ProductDTO("2222", "배", "대과", 2500));
        list.add(new ProductDTO("3333", "복숭아", "딱딱이", 3500));
        list.add(new ProductDTO("4444", "파인애플", "종과", 4500));
        return list;
    }
}
