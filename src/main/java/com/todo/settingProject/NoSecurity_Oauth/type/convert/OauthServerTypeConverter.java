package com.todo.settingProject.NoSecurity_Oauth.type.convert;

import com.todo.settingProject.NoSecurity_Oauth.type.OauthServerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

/**
 * 해당 파일의 경우 OauthServerType을 사용시
 * 자동으로 호출 되어 OauthServerType(String source)를 호출하여
 * 호출된 OauthServerType 타입을 가지고 있는 변수에
 * OauthServerType.typeName(source)를 반환
*/
@Slf4j
public class OauthServerTypeConverter implements Converter<String, OauthServerType> {

    // url에 oauthType 소문자를 대문자로 변경후 enum의 선언값 가져오기
    @Override
    public OauthServerType convert(String source) {
        return OauthServerType.typeName(source);
    }
}