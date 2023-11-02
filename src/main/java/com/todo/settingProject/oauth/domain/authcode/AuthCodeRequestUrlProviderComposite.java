package com.todo.settingProject.oauth.domain.authcode;

import com.todo.settingProject.oauth.type.OauthServerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * oatuth 지원 타입 체크
 * */
@Slf4j
@Component  // bean 생성 어노테이션
public class AuthCodeRequestUrlProviderComposite {

    private final Map<OauthServerType, AuthCodeRequestUrlProvider> mapping;

    /**
     * Bean 등록시 호출됨 - spring 서버 실행 시 호출됨
     * oauth 타입에 따른 implement 호출 후 등록
     */
    public AuthCodeRequestUrlProviderComposite(Set<AuthCodeRequestUrlProvider> providers) {
        mapping = providers.stream()
                .collect(Collectors.toMap(
                        AuthCodeRequestUrlProvider::supportServer,
                        Function.identity()
                ));
    }

    /**
     * kakao 계정정보 조회에 필요한 redirect url 반환
     */
    public String provide(OauthServerType oauthServerType) {
        return getProvider(oauthServerType).provide();
    }

    /**
     * OauthServerType 타입체크
     */
    public AuthCodeRequestUrlProvider getProvider(OauthServerType oauthServerType) {
        return Optional.ofNullable(mapping.get(oauthServerType))
                .orElseThrow(() -> new RuntimeException("지원하지 않는 소셜 로그인 타입입니다."));
    }
}
