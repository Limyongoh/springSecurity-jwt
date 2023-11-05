package com.todo.settingProject.oauth.domain;

import com.todo.settingProject.oauth.domain.entity.OauthUser;
import com.todo.settingProject.oauth.type.OauthServerType;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OauthUserClientComposite {

    private final Map<OauthServerType, OauthUserClient> mapping;

    public OauthUserClientComposite(Set<OauthUserClient> clients) {
        mapping = clients.stream()
                .collect(Collectors.toMap(
                        OauthUserClient::supportServer,
                        Function.identity()
                ));
        System.out.println();
    }

    public OauthUser fetch(OauthServerType oauthServerType, String authCode) {
        return getClient(oauthServerType).fetch(authCode);
    }

    private OauthUserClient getClient(OauthServerType oauthServerType) {
        return Optional.ofNullable(mapping.get(oauthServerType))
                .orElseThrow(() -> new RuntimeException("지원하지 않는 소셜 로그인 타입입니다."));
    }
}