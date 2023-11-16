package com.todo.settingProject.domain.entity.user;

import com.todo.settingProject.NoSecurity_Oauth.type.OauthServerType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OauthId {

    @Column(nullable = false, name = "OAUTH_ID")
    private Long oauthId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "OAUTH_SERVER_TYPE")
    private OauthServerType oauthServerType;

    public Long oauthId() {
        return oauthId;
    }

    public OauthServerType oauthServer() {
        return oauthServerType;
    }
}