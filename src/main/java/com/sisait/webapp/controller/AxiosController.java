package com.sisait.webapp.controller;


import com.sisait.webapp.dto.ProductDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class AxiosController {
    @GetMapping("/axiosGetTest")
    public String axiosGetTest(int now_pages, String searchWord){
        System.out.println("현재페이지" +now_pages+", 검색어"+searchWord);

        return "현재페이지"+now_pages+", 검색어"+searchWord;
    }


    @PostMapping("/axiosPostTest")
    public Map<String, Object> exiosPostTest(@RequestBody ProductDTO dto) {
        System.out.println("상품코드: " + dto.getProName() + ", 가격 : " + dto.getPrice());

        Map<String, Object> mapData = new HashMap<String, Object>();
        mapData.put("productName", dto.getProName());
        mapData.put("getPrice", dto.getPrice());
        List<ProductDTO> lst = new ArrayList<ProductDTO>();
        lst.add(new ProductDTO("1001", "포도", "2Kg", 3000));
        lst.add(new ProductDTO("1002", "딸기", "2팩", 3500));
        lst.add(new ProductDTO("1003", "파인애플", "2개", 4000));
        mapData.put("product", lst);

        return mapData;
    }
}





