package com.todo.settingProject.oauth.authcode;

import com.todo.settingProject.oauth.Type.OauthServerType;

public interface AuthCodeRequestUrlProvider {
    OauthServerType supportServer();
    String provide();
}
