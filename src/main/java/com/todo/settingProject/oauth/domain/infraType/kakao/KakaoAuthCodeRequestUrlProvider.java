package com.todo.settingProject.oauth.domain.infraType.kakao;

import com.todo.settingProject.oauth.type.OauthServerType;
import com.todo.settingProject.oauth.domain.authcode.AuthCodeRequestUrlProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * kakao oauth시 url 생성
 *
 * */
@Component                  // bean 생성 어노테이션
@RequiredArgsConstructor    // lombok 생성자 자동생성 어노테이션
public class KakaoAuthCodeRequestUrlProvider implements AuthCodeRequestUrlProvider {

    private final KakaoOauthConfig kakaoOauthConfig;

    /**
     * KakaoAuthCodeRequestUrlProvider으로 생성된 메서드 호출 시 자동 호출
     */
    @Override
    public OauthServerType supportServer() {
        return OauthServerType.KAKAO;
    }

    @Override
    public String provide() {
        // UriComponentsBuilder : uri를 동적으로 생성해주는 클래스
        // uri로 각각의 링크를 생성할 수 있어 rest 스타일로 개발하는데 편리하다고 함
        return UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com/oauth/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", kakaoOauthConfig.clientId())
                .queryParam("redirect_uri", kakaoOauthConfig.redirectUri())
                .queryParam("scope", String.join(",", kakaoOauthConfig.scope()))
//                .queryParam("prompt", "login")
                .toUriString();
    }
}
