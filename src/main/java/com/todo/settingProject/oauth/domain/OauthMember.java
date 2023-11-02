package com.todo.settingProject.oauth.domain;
import static lombok.AccessLevel.PROTECTED;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class OauthMember {

    private Long id;
    private OauthId oauthId;
    private String nickname;
    private String profileImageUrl;
    private String email;

    public Long id() {
        return id;
    }

    public OauthId oauthId() {
        return oauthId;
    }

    public String nickname() {
        return nickname;
    }

    public String profileImageUrl() {
        return profileImageUrl;
    }

    public String email() {
        return email;
    }
}