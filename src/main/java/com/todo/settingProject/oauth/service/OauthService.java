package com.todo.settingProject.oauth.service;

import com.todo.settingProject.oauth.Type.OauthServerType;
import com.todo.settingProject.oauth.authcode.AuthCodeRequestUrlProviderComposite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor    // lombok 생성자 자동생성 어노테이션
public class OauthService {
    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;

    public String getAuthCodeRequestUrl(OauthServerType oauthServerType){
        return authCodeRequestUrlProviderComposite.provide(oauthServerType);
    }
}
