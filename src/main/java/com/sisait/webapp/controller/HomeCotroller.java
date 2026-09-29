package com.sisait.webapp.controller;

import com.sisait.webapp.dto.TestDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeCotroller {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(){
        System.out.println("home 컨트롤러 실행됨");
        return "index"; //index.html <<
    }

    @GetMapping("/list")
    public String test(int page){
        System.out.println("페이지" + page);
        return "board";
    }

    @RequestMapping(value = "/my/infor", method = RequestMethod.POST)
    public ModelAndView information(String username, int age){
        System.out.println("이름" + username);
        System.out.println("나이" + age);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("board");
        return mav;
    }

    @PostMapping("/my/infor2")
    public ModelAndView information2(TestDTO dto){
        System.out.println("이름" + dto.getUsername());
        System.out.println("아이디" + dto.getUserid());
        System.out.println("나이" + dto.getAge());
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    @GetMapping("/test/{won}/{product}")
    public String test2(@PathVariable int won ,@PathVariable String product){
        System.out.println("won -> " + (won+10000) + "만");
        System.out.println("product -> " + product);
        return "index";
    }



    // 2. Query Parameter 방식 (/test?won=25000) - 새로 추가할 코드!
    @GetMapping("/test")
    public String testParam(int won) {
        System.out.println("won -> " + won);
        return "index";
    }
}