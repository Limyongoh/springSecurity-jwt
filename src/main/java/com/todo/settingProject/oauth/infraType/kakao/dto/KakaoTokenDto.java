package com.todo.settingProject.oauth.infraType.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * 카카오에서 토큰 응답 dto
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoTokenDto(
    String tokenType,               // 토큰타입 ('bearer'로 고정된다고함)
    String accessToken,             // 사용자 액세스 토큰 값
    String idToken,                 // id 토큰값 (OpenID Connect 확장 기능을 통해 발급되는 ID 토큰, Base64 인코딩 된 사용자 인증 정보 포함)
    Integer expiresIn,              // 액세스 토큰과 ID 토큰의 만료 시간[초] (액세스 토큰과 id 토큰의 만료시간 동일 하다고함)
    String refreshToken,            // 사용자 리프레시 토큰 값
    Integer refreshTokenExpiresIn   // 리프레시 토큰 만료 시간[초]

) {}
