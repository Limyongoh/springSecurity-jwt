package com.todo.settingProject.oauth.domain.infraType.kakao.client;


import com.todo.settingProject.oauth.domain.infraType.kakao.KakaoOauthConfig;
import com.todo.settingProject.oauth.domain.infraType.kakao.dto.KakaoUserResponse;
import com.todo.settingProject.oauth.domain.infraType.kakao.dto.KakaoTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/*
        but porxy 지정 필요 -> HttpInterfaceConfig.java 파일 생성

        APPLICATION_FORM_URLENCODED_VALUE -> kakao에서 원하는 헤더 타입
        MediaType 안에 여러가지 타입 선언되어 있음 나중에 확인해 볼것

        MultiValueMap -> 하나의 key값으로 여러개의 value를 가지고 있음
        - Map과 차이
            -> ex)
                Map<String, Integer> map = new HashMap();
                MultiValueMap<String,Integer> multiValueMap = new LinkedMultiValueMap<>();
                basicMap.put("test",1);
                basicMap.put("test",2);
                multiValueMap.add("test",1);
                multiValueMap.add("test",2);
                ==>
                    map = {test=2}
                    multiValueMap = {test=[1, 2]}
     */

/**
 * KAKAO api 요청
 * RestTemplate을 통해 api 통신
 */
@Component
@RequiredArgsConstructor
public class KakaoApiClientImpl {
    private final KakaoOauthConfig kakaoOauthConfig;

    /**
     * kakao api를 통해 get Token
     */
    public KakaoTokenDto getToken(String authCode){
        String tokenURL = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<?> entity = new HttpEntity<>(tokenRequestParams(authCode),headers);
        ResponseEntity<KakaoTokenDto> response = restTemplate.postForEntity(tokenURL,entity,KakaoTokenDto.class);

        return response.getBody();
    }

    /**
     * kakao api를 통해 get Token 시 필요 params
     */
    private MultiValueMap<String, String> tokenRequestParams(String authCode){
        MultiValueMap<String, String> kakaTokenParam = new LinkedMultiValueMap<>();

        kakaTokenParam.add("grant_type","authorization_code");
        kakaTokenParam.add("client_id", kakaoOauthConfig.clientId());
        kakaTokenParam.add("redirect_uri", kakaoOauthConfig.redirectUri());
        kakaTokenParam.add("code", authCode);
        kakaTokenParam.add("client_secret", kakaoOauthConfig.clientSecret());

        return kakaTokenParam;
    }


    /**
     * kakao api를 통해 get User
     */
    public KakaoUserResponse getUser(String bearerToken){
        String tokenURL = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.set("Authorization", bearerToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<KakaoUserResponse>  response = restTemplate.postForEntity(tokenURL,entity, KakaoUserResponse.class);

        return response.getBody();

    }

}