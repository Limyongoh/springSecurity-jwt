package com.todo.settingProject.oauth.service;


import com.todo.settingProject.domain.entity.user.User;
import com.todo.settingProject.domain.repository.user.UserRepository;
import com.todo.settingProject.oauth.providerType.common.CustomOauthUser;
import com.todo.settingProject.oauth.providerType.common.OauthAttributes;
import com.todo.settingProject.oauth.providerType.kakao.KakaoInfo;
import com.todo.settingProject.oauth.type.OauthServerType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class OauthCustom implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("loadUser 탔나?");

        Assert.notNull(userRequest, "userRequest not null");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> oauthService = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = oauthService.loadUser(userRequest);


        // oauth Type의 값이 들어감 ex) kakao의 경우 kakao, google경우 google, naver의 경우 naver 로 추측
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // naver, kakao -> id,   google -> sub 로 key값을 통해 구분pk 값을 가진다고함
        String userNameAttuributeName = userRequest.getClientRegistration()
                                                    .getProviderDetails()
                                                    .getUserInfoEndpoint()
                                                    .getUserNameAttributeName();

        Map<String, Object> attributes  = oAuth2User.getAttributes();

        OauthAttributes extractAttributes  = OauthAttributes.OauthTypeOf(userNameAttuributeName, attributes);
        OauthServerType  oauthServerType = getOauthType(registrationId);

        User createUser = saveAndfindUser(extractAttributes, oauthServerType);
        log.info("OAuth2User loadUser  호출" +
                ": {}", oAuth2User);
        System.out.println("AAAAA");

        return new CustomOauthUser(
                Collections.singleton(new SimpleGrantedAuthority("user")),
                attributes,
                extractAttributes.getNameAttributeKey(),
                createUser.getEmail()
        );
    }


    private OauthServerType getOauthType(String registrationId) {
        if(OauthServerType.typeName(registrationId).equals(OauthServerType.NAVER)) {
            return OauthServerType.NAVER;
        }
        if(OauthServerType.typeName(registrationId).equals(OauthServerType.GOOGLE)) {
            return OauthServerType.GOOGLE;
        }
        return OauthServerType.KAKAO;
    }

    private User saveAndfindUser(OauthAttributes oauthAttributes, OauthServerType oauthServerType){
        log.info("oauthAttributes.getOauthInfo().id()의 값 ----> "+ oauthAttributes.getOauthInfo().id());
        log.info("oauthServerType ---의 값 ---> "+oauthServerType);
        User user = userRepository.findByOauthIdAndOauthServerType(oauthAttributes.getOauthInfo().id(), oauthServerType).orElse(null);
        if(user == null){// 계정 없을 경우 저장
            return saveUser(oauthAttributes,oauthServerType);
        }

        return user;
    }

    // 처음 로그인한 oauth 계정의 경우 db저장
    private User saveUser(OauthAttributes oauthAttributes, OauthServerType oauthServerType){
       log.info("oauthServerType ==> "+oauthServerType);
       log.info("oauthAttributes.getOauthInfo()==> "+oauthAttributes.getOauthInfo());
        User createUser = oauthAttributes.toUserEntity(oauthServerType, oauthAttributes.getOauthInfo());
        return userRepository.save(createUser);
    }


}
