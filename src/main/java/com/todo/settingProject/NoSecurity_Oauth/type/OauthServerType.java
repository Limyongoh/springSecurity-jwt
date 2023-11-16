package com.todo.settingProject.NoSecurity_Oauth.type;

import java.util.Locale;

// oauth 타입 선언(kakao, naver, google ... 등등)
public enum OauthServerType {
    KAKAO,
    NAVER,
    GOOGLE
    ;

    // type 대문자로 변경 후 enum 값 받아옴
    public static OauthServerType typeName(String type){
        return OauthServerType.valueOf(type.toUpperCase(Locale.ENGLISH));
    }

}
