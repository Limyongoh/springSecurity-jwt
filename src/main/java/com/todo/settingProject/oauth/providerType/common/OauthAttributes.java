package com.todo.settingProject.oauth.providerType.common;

import com.todo.settingProject.domain.entity.user.User;
import com.todo.settingProject.oauth.providerType.kakao.KakaoInfo;
import com.todo.settingProject.oauth.type.OauthServerType;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
public class OauthAttributes {
    private String nameAttributeKey;
    private OauthInfo oauthInfo;

    @Builder
    public OauthAttributes(String nameAttributeKey, OauthInfo oauthInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauthInfo = oauthInfo;
    }

    public static OauthAttributes OauthTypeOf(String nameAttributeKey, Map<String, Object> attributes){
//        if(oauthServerType == OauthServerType.KAKAO){
//            return ofKakao(oauthServerType, attributes);
//        }
        // 현재 kakao만 구현되어 있으므로 우선 하단의 내용으로 조치
        return  ofKakao(nameAttributeKey, attributes);
    }

    public static OauthAttributes ofKakao(String nameAttributeKey, Map<String, Object> attributes){
        return OauthAttributes.builder()
                .nameAttributeKey(nameAttributeKey)
                .oauthInfo(new KakaoInfo(attributes))
                .build();
    }

    public User toUserEntity(OauthServerType oauthServerType, OauthInfo oauthInfo){
        return User.builder()
                .oauthServerType(oauthServerType)
                .oauthId(oauthInfo.id())
                .email(UUID.randomUUID()+"@socail.com")
                .nickName(oauthInfo.nickname())
                .build();
    }
}
