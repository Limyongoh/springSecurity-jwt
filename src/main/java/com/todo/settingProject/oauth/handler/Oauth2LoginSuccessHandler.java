package com.todo.settingProject.oauth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("소셜 로그인 성공!");
        System.out.println("authentication.getPrincipal 값 확인후 토큰 재발급" +
                "진행하기");


        HttpSession session = request.getSession();
        session.setAttribute("greeting", authentication.getName() + "님 반갑습니다.");
        response.sendRedirect("http://localhost:3000/content");

    }
}
