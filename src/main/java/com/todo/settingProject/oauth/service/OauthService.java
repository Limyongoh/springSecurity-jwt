package com.todo.settingProject.oauth.service;

import com.todo.settingProject.domain.entity.user.User;
import com.todo.settingProject.domain.repository.user.UserRepository;
import com.todo.settingProject.oauth.domain.entity.OauthUser;
import com.todo.settingProject.oauth.domain.OauthUserClientComposite;
import com.todo.settingProject.oauth.repository.oauthuser.OauthUserRepository;
import com.todo.settingProject.oauth.type.OauthServerType;
import com.todo.settingProject.oauth.domain.authcode.AuthCodeRequestUrlProviderComposite;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor    // lombok 생성자 자동생성 어노테이션
public class OauthService {
    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private final OauthUserClientComposite oauthUserClientComposite;
    private final OauthUserRepository oauthUserRepository;
    private final UserRepository userRepository;

    /**
     * redirect URL 반환
     */
    public String getAuthCodeRequestUrl(OauthServerType oauthServerType){
        return authCodeRequestUrlProviderComposite.provide(oauthServerType);
    }

    /**
     * oauth 로그인
     */
    @Transactional
    public OauthUser login(OauthServerType oauthServerType, String authCode) {
        OauthUser oauthUser = oauthUserClientComposite.fetch(oauthServerType, authCode);

        Optional<OauthUser> optionalOauthUser = oauthUserRepository.findByOauthId(oauthUser.getOauthId());
        // 계정정보 없을경우 oauth_user 회원가입
        if(!optionalOauthUser.isPresent()){
            User user = User.builder()
                    .email(oauthUser.getEmail())
                    .password("")
                    .nickName(oauthUser.getNickname())
                    .oauthAt(true)
                    .build();

            userRepository.save(user);

            OauthUser oauth = OauthUser.builder()
                    .user(user)
                    .email(oauthUser.getEmail())
                    .nickname(oauthUser.getNickname())
                    .oauthId(oauthUser.getOauthId())
                    .build();
            oauthUserRepository.save(oauth);
        }

        return oauthUser;
    }
}
