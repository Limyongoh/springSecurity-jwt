package com.todo.settingProject.oauth.handler;

import com.todo.settingProject.jwt.service.JwtService;
import com.todo.settingProject.oauth.providerType.common.CustomOauthUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("소셜 로그인 성공!");
        System.out.println("authentication.getPrincipal 값 확인후 토큰 재발급" +
                "진행하기");


        Object principal = authentication.getPrincipal();
        CustomOauthUser  oAuth2User = (CustomOauthUser) authentication.getPrincipal();

        String accessToken = jwtService.createAccsessToken(oAuth2User.getEmail());
        response.addHeader(jwtService.getAccessHeader(),"Bearer " + accessToken);
        response.sendRedirect("http://localhost:3000/content");// oauth 처음 로그인시 추가정보 입력 front url

        loginSuccess(response, oAuth2User);

        HttpSession session = request.getSession();
        session.setAttribute("greeting", authentication.getName() + "님 반갑습니다.");

    }

    private void loginSuccess(HttpServletResponse response, CustomOauthUser oAuth2User){
        String accessToken = jwtService.createAccsessToken(oAuth2User.getEmail());
        String refreshToken = jwtService.createRefreshToken();
        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefeshToken(oAuth2User.getEmail(), refreshToken);
    }
}
