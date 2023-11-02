package com.todo.settingProject.oauth.service;

import com.todo.settingProject.oauth.domain.OauthMember;
import com.todo.settingProject.oauth.domain.OauthMemberClientComposite;
import com.todo.settingProject.oauth.type.OauthServerType;
import com.todo.settingProject.oauth.domain.authcode.AuthCodeRequestUrlProviderComposite;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor    // lombok 생성자 자동생성 어노테이션
public class OauthService {
    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private final OauthMemberClientComposite oauthMemberClientComposite;

    /**
     * redirect URL 반환
     */
    public String getAuthCodeRequestUrl(OauthServerType oauthServerType){
        return authCodeRequestUrlProviderComposite.provide(oauthServerType);
    }

    /**
     * oauth 로그인
     */
    public OauthMember login(OauthServerType oauthServerType, String authCode) {
        OauthMember oauthMember = oauthMemberClientComposite.fetch(oauthServerType, authCode);
        //OauthMember saved = oauthMemberRepository.findByOauthId(oauthMember.oauthId())
        //        .orElseGet(() -> oauthMemberRepository.save(oauthMember));
        return oauthMember;
    }
}
