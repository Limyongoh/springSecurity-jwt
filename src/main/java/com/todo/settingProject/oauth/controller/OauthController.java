package com.todo.settingProject.oauth.controller;

import com.todo.settingProject.oauth.domain.entity.OauthUser;
import com.todo.settingProject.oauth.type.OauthServerType;
import com.todo.settingProject.oauth.domain.infraType.kakao.client.KakaoApiClientImpl;
import com.todo.settingProject.oauth.service.OauthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

// oauth 타입별 로그인 기능 구현중
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;

    private final KakaoApiClientImpl kakaoApiClientImpl;

    /**
     * kakao Redirect url 받아오기
     */
    @GetMapping("/{oauthServerType}")
    public ResponseEntity<Void> kakao(@PathVariable OauthServerType oauthServerType,
                                HttpServletResponse response) throws IOException {

        String redirectUrl = oauthService.getAuthCodeRequestUrl(oauthServerType);
        response.sendRedirect(redirectUrl);
        return ResponseEntity.ok().build();
    }

    /**
     * kakao 로그인
     */
    @GetMapping("/login/{oauthServerType}")
    public ResponseEntity loginKakao(@PathVariable OauthServerType oauthServerType,
                                     @RequestParam String code){
        OauthUser oauthUser = oauthService.login(oauthServerType, code);
        return ResponseEntity.ok("이름"+ oauthUser.getNickname()+"\n email "+ oauthUser.getEmail()
        );
    }

}
