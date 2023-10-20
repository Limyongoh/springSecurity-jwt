package com.todo.settingProject.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class KakaoLoginController {

    @GetMapping("/kakao")
    public String test(){
        return "테스트";
    }
}
