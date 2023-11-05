package com.todo.settingProject.domain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test") public class TestController {

    @GetMapping("/home")//노트북에서 커밋 테스트
    public String home(){
        return "home comming";
    }
}
