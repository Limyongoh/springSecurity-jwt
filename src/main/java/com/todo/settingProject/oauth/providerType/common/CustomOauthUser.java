package com.todo.settingProject.oauth.providerType.common;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOauthUser extends DefaultOAuth2User {

    // oauth2사용시 각 소셜의 id값 //
    private String email;


    /**
     * Constructs a {@code DefaultOAuth2User} using the provided parameters.
     *
     * @param authorities      the authorities granted to the user
     * @param attributes       the attributes about the user
     * @param nameAttributeKey the key used to access the user's &quot;name&quot; from
     *                         {@link #getAttributes()}
     */
    public CustomOauthUser(Collection<? extends GrantedAuthority> authorities,
                           Map<String, Object> attributes,
                           String nameAttributeKey,
                           String email) {
        super(authorities, attributes, nameAttributeKey);
        this.email = email;
    }
}
