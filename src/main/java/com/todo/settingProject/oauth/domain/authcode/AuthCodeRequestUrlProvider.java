package com.todo.settingProject.oauth.domain.authcode;

import com.todo.settingProject.oauth.type.OauthServerType;

/**
 * OauthServerType 타입별로 생성한 implement의 provide호출
 * 현재 kakao만 구현
 * 추후에 다른 로그인 구현예정
 */
public interface AuthCodeRequestUrlProvider {
    OauthServerType supportServer();
    String provide();
}