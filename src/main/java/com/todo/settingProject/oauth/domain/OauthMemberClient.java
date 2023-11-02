package com.todo.settingProject.oauth.domain;

import com.todo.settingProject.oauth.type.OauthServerType;

/**
 * oauth별 implement생성후 OauthServerType별  fetch에서 api호출
 */
public interface OauthMemberClient {
    OauthServerType supportServer();

    OauthMember fetch(String code);
}