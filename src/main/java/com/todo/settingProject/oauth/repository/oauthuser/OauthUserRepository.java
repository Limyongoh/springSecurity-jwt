package com.todo.settingProject.oauth.repository.oauthuser;

import com.todo.settingProject.oauth.common.entiy.OauthId;
import com.todo.settingProject.oauth.domain.entity.OauthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OauthUserRepository extends JpaRepository<OauthUser,Long> {
    Optional<OauthUser> findByEmail(String email);

    Optional<OauthUser> findByOauthId(OauthId oauthId);
}