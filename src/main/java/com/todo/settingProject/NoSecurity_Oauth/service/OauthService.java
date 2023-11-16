package com.todo.settingProject.NoSecurity_Oauth.service;

import com.todo.settingProject.domain.entity.user.User;
import com.todo.settingProject.NoSecurity_Oauth.type.OauthServerType;
//import org.springframework.security.crypto.password.PasswordEncoder;


public interface OauthService {
    /**
     * redirect URL 반환
     */
    String getAuthCodeRequestUrl(OauthServerType oauthServerType);

    /**
     * oauth 로그인
     */
    User login(OauthServerType oauthServerType, String authCode);
}
