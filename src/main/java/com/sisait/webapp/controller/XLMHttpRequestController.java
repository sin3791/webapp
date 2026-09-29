package com.sisait.webapp.controller;


import com.sisait.webapp.dto.ProductDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List; // 1. List import 추가
@CrossOrigin(origins = "*")
@RestController
public class XLMHttpRequestController {

    @GetMapping("/xmlTest")
    public String xmlTest(String name, int age){
        System.out.println("리액트에서 보낸 데이터");
        System.out.println("이름: "+ name + ", 나이:" + age);

        // 리액트에게 정보 보내기
        return "name: "+ name + "님, age:" + age;
    }

    @PostMapping("/xmlTest2")
    public String xmlTest2(String proCode, String proName, String option, int price){
        System.out.println("상품코드: " +proCode);
        System.out.println("상품명: " +proName);
        System.out.println("옵션: " +option);
        System.out.println("가격: " +price);

        return proCode+"/"+proName+"/"+option+"/"+price;
    }
    @GetMapping("/xmlTest3")
    public List<ProductDTO> xmlTest3(){
        List<ProductDTO> lst = new ArrayList<ProductDTO>();
        lst.add(new ProductDTO("1111", "냉장고", "888", 12000));
        lst.add(new ProductDTO("2222", "텔레비전", "85인치", 15000));
        lst.add(new ProductDTO("3333", "세탁기", "25인치", 19000));
        return lst;
    }
}
