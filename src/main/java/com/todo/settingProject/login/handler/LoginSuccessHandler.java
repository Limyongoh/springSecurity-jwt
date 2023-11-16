package com.todo.settingProject.login.handler;

import com.todo.settingProject.domain.repository.user.UserRepository;
import com.todo.settingProject.jwt.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        String email = exportUsername(authentication); // 인증정보(Authentication)에서 email 가져오기
        String accessToken = jwtService.createAccsessToken(email);// 해당 email의 access Token 생성
        String refreshToken = jwtService.createRefreshToken(); // refresh Token 생성

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);

        userRepository.findByEmail(email).ifPresent(user -> {
                user.updateRefreshToken(refreshToken); // refresh Token 갱신
                userRepository.saveAndFlush(user);
            }
        );
        log.info("로그인 성공! 이메일 : {}", email);
        log.info("로그인 성공! accessToken : {}", accessToken);

    }

    private String exportUsername (Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
