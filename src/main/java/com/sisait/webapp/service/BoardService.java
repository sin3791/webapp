package com.sisait.webapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor // private final로 선언된 변수의 객체 생성

public class BoardService {
    private final BoardService service;


}
