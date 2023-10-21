package com.todo.settingProject.oauth.convert;

import com.todo.settingProject.oauth.Type.OauthServerType;
import org.springframework.core.convert.converter.Converter;

public class OauthServerTypeConverter implements Converter<String, OauthServerType> {

    // url에 oauthType 소문자를 대문자로 변경후 enum의 선언값 가져오기
    @Override
    public OauthServerType convert(String source) {
        return OauthServerType.typeName(source);
    }
}