package com.todo.settingProject.oauth.providerType.common;

import java.util.Map;

// abstract를 통해 추상화 class 로 선언하여 각 oauth의 [타입별]info.class를 사용해 값 세팅
public abstract class OauthInfo {
    protected Map<String, Object> attributes;

    public OauthInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract Long id();

    public abstract String nickname();

    public abstract String email();



}
