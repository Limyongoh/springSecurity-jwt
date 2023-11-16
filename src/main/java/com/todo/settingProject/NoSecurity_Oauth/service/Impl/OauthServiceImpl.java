package com.todo.settingProject.NoSecurity_Oauth.service.Impl;

import com.todo.settingProject.domain.entity.user.User;
import com.todo.settingProject.domain.repository.user.UserRepository;
import com.todo.settingProject.NoSecurity_Oauth.provider.OAuthCodeRequestProvider;
import com.todo.settingProject.NoSecurity_Oauth.service.OauthService;
import com.todo.settingProject.NoSecurity_Oauth.type.OauthServerType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor    // lombok 생성자 자동생성 어노테이션
public class OauthServiceImpl implements OauthService {
    private final OAuthCodeRequestProvider oAuthCodeRequestProvider;
    //private final OauthUserRepository oauthUserRepository;
    private final UserRepository userRepository;


    /**
     * redirect URL 반환
     */
    @Override
    public String getAuthCodeRequestUrl(OauthServerType oauthServerType) {
        return oAuthCodeRequestProvider.oauthProviderRedirect(oauthServerType);
    }

    /**
     * oauth 로그인
     */
    @Override
    @Transactional
    public User login(OauthServerType oauthServerType, String authCode) {
        User user = oAuthCodeRequestProvider.oauthLogin(oauthServerType, authCode);

        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        // 계정정보 없을경우 oauth_user 회원가입
        if(!optionalUser.isPresent()){
            User newUser = User.builder()
                    .email(user.getEmail())
                    .password("password")
                    .nickName(user.getNickName())
                    .name("")
                    //.phoneNumber("")
                    .oauthAt(true)
                    .createBy(user.getEmail())
                    .build();

            userRepository.save(user);

//            OauthUser oauth = OauthUser.builder()
//                    .user(user)
//                    .email(oauthUser.getEmail())
//                    .nickname(oauthUser.getNickname())
//                    .oauthId(oauthUser.getOauthId())
//                    .name("")
//                    //.phoneNumber("")
//                    .createBy(oauthUser.getEmail())
//                    .build();
//            oauthUserRepository.save(oauth);
        }
        return user;
    }
}
