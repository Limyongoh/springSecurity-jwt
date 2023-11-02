package com.todo.settingProject.oauth.infraType.kakao.client;

import com.todo.settingProject.oauth.domain.OauthMember;
import com.todo.settingProject.oauth.domain.OauthMemberClient;
import com.todo.settingProject.oauth.type.OauthServerType;
import com.todo.settingProject.oauth.infraType.kakao.KakaoOauthConfig;
import com.todo.settingProject.oauth.infraType.kakao.dto.KakaoMemberResponse;
import com.todo.settingProject.oauth.infraType.kakao.dto.KakaoTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor
public class KakaoMemberClient implements OauthMemberClient {
    // RestTemplate을 통해 api 통신
    private final KakaoApiClientImpl kakaoApiClientImpl;

    @Override
    public OauthServerType supportServer() {
        return OauthServerType.KAKAO;
    }

    @Override
    public OauthMember fetch(String authCode) {
        KakaoTokenDto tokenInfo = kakaoApiClientImpl.getToken(authCode);
        KakaoMemberResponse kakaoMemberResponse = kakaoApiClientImpl.getUser("Bearer " + tokenInfo.accessToken());

        return kakaoMemberResponse.toDomain();  // (3)
    }

}