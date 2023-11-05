package com.todo.settingProject.oauth.domain.entity;

import com.todo.settingProject.domain.common.entity.Base;
import com.todo.settingProject.domain.entity.user.User;
import com.todo.settingProject.oauth.common.entiy.OauthId;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity(name = "TBL_OAUTH_USER")
public class OauthUser extends Base {

    //    private Long id;
    //private String profileImageUrl;
    @OneToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @Column(name = "NICK_NAME")
    private String nickname;

    @Column(name = "EMAIL")
    private String email;

    @Embedded
    private OauthId oauthId;

    @Builder
    public OauthUser(User user,
                              String nickname,
                              String email,
                              OauthId oauthId) {
        this.user = user;
        this.nickname = nickname;
        this.email = email;
        this.oauthId = oauthId;
    }

}