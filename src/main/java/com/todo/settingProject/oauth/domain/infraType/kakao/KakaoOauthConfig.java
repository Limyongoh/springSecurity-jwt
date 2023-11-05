package com.todo.settingProject.oauth.domain.infraType.kakao;

/*

 카카오에서 로그인 기능 구현시 필요한 정보를 담고 있는 클래스
 record란 데이터의 정보만 가지고 있는 데이터 클래스라고 하며
 class로 변수선언 시 setter,getter를선언한 것과 아래 형식으로 변수선언한것과 동일하다고함
 따라서 dto 같은 경우? 사용하면 편할듯?
 나중에 시간내서 따로 알아봐야겠음
*/

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.kakao")
public record KakaoOauthConfig(
        String redirectUri,
        String clientId,
        String clientSecret,
        String[] scope
) {}