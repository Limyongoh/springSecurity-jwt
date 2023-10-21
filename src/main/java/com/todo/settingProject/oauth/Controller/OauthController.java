package com.todo.settingProject.oauth.Controller;

import com.todo.settingProject.oauth.Type.OauthServerType;
import com.todo.settingProject.oauth.service.OauthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;


    /**
     *
     * oauth 타입별 로그인 기능 구현중
     *
     */
    @GetMapping("/{oauthServerType}")
    public ResponseEntity<Void> kakao(@PathVariable OauthServerType oauthServerType,
                                HttpServletResponse response) throws IOException {

        String redirectUrl = oauthService.getAuthCodeRequestUrl(oauthServerType);
        response.sendRedirect(redirectUrl);
        //response.sendRedirect("http://localhost:3000");
        return ResponseEntity.ok().build();
    }
}
