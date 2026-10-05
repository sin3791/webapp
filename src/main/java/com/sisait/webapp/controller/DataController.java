package com.sisait.webapp.controller;


import com.sisait.webapp.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {
    private final DataService service;


}
