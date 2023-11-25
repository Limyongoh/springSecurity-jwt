package com.todo.settingProject.domain.repository.user;

import com.todo.settingProject.domain.entity.user.User;
import com.todo.settingProject.oauth.type.OauthServerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByNickName(String nickName);

    Optional<User> findByRefreshToken(String refreshToken);

    Optional<User> findByOauthIdAndOauthServerType(Long oauthId, OauthServerType oauthServerType);
}