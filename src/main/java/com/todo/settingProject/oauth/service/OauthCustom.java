package com.todo.settingProject.oauth.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Service
@Slf4j
public class OauthCustom implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("loadUser 탔나?");

        Assert.notNull(userRequest, "userRequest not null");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> oauthService = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = oauthService.loadUser(userRequest);

        // oauth Type의 값이 들어감
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttuributeName = userRequest.getClientRegistration()
                                                    .getProviderDetails()
                                                    .getUserInfoEndpoint()
                                                    .getUserNameAttributeName();
        log.info("OAuth2User loadUser  호출" +
                ": {}", oAuth2User);
        System.out.println("AAAAA");
        return oAuth2User;
    }



}
