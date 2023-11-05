package com.todo.settingProject.oauth.domain.infraType.kakao.client;

import com.todo.settingProject.oauth.domain.entity.OauthUser;
import com.todo.settingProject.oauth.domain.OauthUserClient;
import com.todo.settingProject.oauth.type.OauthServerType;
import com.todo.settingProject.oauth.domain.infraType.kakao.dto.KakaoUserResponse;
import com.todo.settingProject.oauth.domain.infraType.kakao.dto.KakaoTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoUserClient implements OauthUserClient {
    // RestTemplate을 통해 api 통신
    private final KakaoApiClientImpl kakaoApiClientImpl;

    @Override
    public OauthServerType supportServer() {
        return OauthServerType.KAKAO;
    }

    @Override
    public OauthUser fetch(String authCode) {
        KakaoTokenDto tokenInfo = kakaoApiClientImpl.getToken(authCode);
        KakaoUserResponse kakaoUserResponse = kakaoApiClientImpl.getUser("Bearer " + tokenInfo.accessToken());

        return kakaoUserResponse.toDomain();  // (3)
    }

}