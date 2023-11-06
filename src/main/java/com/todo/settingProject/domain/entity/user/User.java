package com.todo.settingProject.domain.entity.user;

import com.todo.settingProject.domain.common.entity.Base;
import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.type.YesNoConverter;

@Entity
@Table(name = "TBL_USER")
public class User extends Base {
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "OAUTH_AT")
    @Convert(converter = YesNoConverter.class)
    private boolean oauthAt;

    @Column(name = "NICK_NAME")
    private String nickName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "NAME")
    private String name;

    @Builder public User(String email,
                String password,
                boolean oauthAt,
                String nickName,
                String phoneNumber,
                String name) {
        this.email = email;
        this.password = password;
        this.oauthAt = oauthAt;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    // 생성자, 게터, 세터, 다른 메서드
}