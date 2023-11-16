package com.todo.settingProject.domain.entity.user;

import com.todo.settingProject.domain.common.entity.Base;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.type.YesNoConverter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자 만들어줌
/*
@DynamicUpdate
update 할때 실제 값이 변경됨 컬럼으로만 update 쿼리를 만듬 - > 컬럼이 많을경우 (15~20) 사용하는것이 좋음
-> 이유 : 변경이 된것이 있는지 추가적인 연산이 필요하게됨
    =>, 변경된 컬럼에 대해서만 update 쿼리를 날리게 되면 이런 SQL 캐시 히트율이 떨어지게 될 것이다.
        출처: https://multifrontgarden.tistory.com/299 [우리집앞마당:티스토리]
 */
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

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @Embedded
    private OauthId oauthId;


    @Builder
    public User(String email,
                String password,
                boolean oauthAt,
                String nickName,
                String phoneNumber,
                String name,
                String refreshToken,
                OauthId oauthId,
                String createBy) {
        this.email = email;
        this.password = password;
        this.oauthAt = oauthAt;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.refreshToken = refreshToken;
        this.oauthId = oauthId;
        this.createBy = createBy;
    }

    // 비밀번호 암호화
    public void setPasswordEncoder(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
    }

    // refreshToken update
    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

}