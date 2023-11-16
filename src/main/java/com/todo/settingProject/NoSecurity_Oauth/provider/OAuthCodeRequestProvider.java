package com.todo.settingProject.NoSecurity_Oauth.provider;

import com.todo.settingProject.domain.entity.user.User;
import com.todo.settingProject.NoSecurity_Oauth.infraType.kakao.KakaoAuthCodeRequestUrlProvider;
import com.todo.settingProject.NoSecurity_Oauth.infraType.kakao.client.KakaoApiClient;
import com.todo.settingProject.NoSecurity_Oauth.infraType.kakao.dto.KakaoTokenDto;
import com.todo.settingProject.NoSecurity_Oauth.infraType.kakao.dto.KakaoUserResponse;
import com.todo.settingProject.NoSecurity_Oauth.type.OauthServerType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor    // lombok 생성자 자동생성 어노테이션
public class OAuthCodeRequestProvider {
    private final KakaoAuthCodeRequestUrlProvider kakaoAuthCodeRequestUrlProvider;
    private final KakaoApiClient kakaoApiClient;

    public String oauthProviderRedirect(OauthServerType oauthServerType){ // redirect 받아올때 소셜 타입 체크
        String redirectURL;
        switch (oauthServerType){
            case KAKAO :{
                redirectURL = kakaoAuthCodeRequestUrlProvider.provide();
                break;
            }
            /*case NAVER : {
                System.out.println("naver");
                break;
            }
            case NAVER : {
                System.out.println("naver");
                break;
            }*/
            default:{
                throw new IllegalArgumentException("지원하지 않는 소셜 로그인 타입입니다.");
            }
        }
        return redirectURL;
    }

    public User oauthLogin(OauthServerType oauthServerType, String code){// 로그인시 계정정보 받아올때 소셜 타입 체크
        User userInfo;
        switch (oauthServerType){
            case KAKAO : {
                KakaoTokenDto tokenInfo = kakaoApiClient.getToken(code);
                KakaoUserResponse kakaoUserResponse = kakaoApiClient.getUser("Bearer " + tokenInfo.accessToken());
                userInfo = kakaoUserResponse.toDomain();
                break;
            }
            /*case NAVER : {
                System.out.println("naver");
                break;
            }
            case NAVER : {
                System.out.println("naver");
                break;
            }*/
            default:{
                throw new IllegalArgumentException("지원하지 않는 소셜 로그인 타입입니다.");
            }
        }
        return userInfo;
    }


}
