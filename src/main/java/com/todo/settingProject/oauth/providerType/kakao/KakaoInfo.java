package com.todo.settingProject.oauth.providerType.kakao;

import com.todo.settingProject.oauth.providerType.common.OauthInfo;
import lombok.Getter;

import java.util.Map;

@Getter
public class KakaoInfo extends OauthInfo {
    public KakaoInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public Long id() {
        return (Long) attributes.get("id");
    }

    @Override
    public String nickname() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        return (String) profile.get("nickname");
    }

    @Override
    public String email() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        return (String) kakaoAccount.get("email");
    }
}