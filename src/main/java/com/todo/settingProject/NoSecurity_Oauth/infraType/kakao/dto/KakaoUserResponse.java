package com.todo.settingProject.NoSecurity_Oauth.infraType.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.todo.settingProject.domain.entity.user.OauthId;
import com.todo.settingProject.domain.entity.user.User;
import com.todo.settingProject.NoSecurity_Oauth.type.OauthServerType;

import java.time.LocalDateTime;

/**
 * kakao 계정정보(json형태)  mapping 처리
 */
@JsonNaming(SnakeCaseStrategy.class)
public record KakaoUserResponse(
        Long id,
        boolean hasSignedUp,
        LocalDateTime connectedAt,
        KakaoAccount kakaoAccount
) {

    public User toDomain() {
        return User.builder()
                //.oauthId(new OauthId(id, OauthServerType.KAKAO))
                .nickName(kakaoAccount.profile.nickname)
                //.profileImageUrl(kakaoAccount.profile.profileImageUrl)
                .email(kakaoAccount.email)
                .build();
    }

    @JsonNaming(SnakeCaseStrategy.class)
    public record KakaoAccount(
            boolean profileNeedsAgreement,
            boolean profileNicknameNeedsAgreement,
            boolean profileImageNeedsAgreement,
            Profile profile,
            boolean nameNeedsAgreement,
            String name,
            boolean emailNeedsAgreement,
            boolean isEmailValid,
            boolean isEmailVerified,
            String email,
            boolean ageRangeNeedsAgreement,
            String ageRange,
            boolean birthyearNeedsAgreement,
            String birthyear,
            boolean birthdayNeedsAgreement,
            String birthday,
            String birthdayType,
            boolean genderNeedsAgreement,
            String gender,
            boolean phoneNumberNeedsAgreement,
            String phoneNumber,
            boolean ciNeedsAgreement,
            String ci,
            LocalDateTime ciAuthenticatedAt
    ) {
    }

    @JsonNaming(SnakeCaseStrategy.class)
    public record Profile(
            String nickname,
            String thumbnailImageUrl,
            String profileImageUrl,
            boolean isDefaultImage
    ) {}
}