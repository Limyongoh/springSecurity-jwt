package com.todo.settingProject.jwt.filter;

import com.todo.settingProject.domain.entity.user.User;
import com.todo.settingProject.domain.repository.user.UserRepository;
import com.todo.settingProject.jwt.utils.PasswordUtil;
import com.todo.settingProject.jwt.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    private final String NO_CHECK_URL = "/login";

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // '/login' 요청이 들어올 경우 해당 필터 종료 후 다음 필터 사용
        log.info("request.getRequestURI() ---> "+request.getRequestURI());
        if(request.getRequestURI().equals(NO_CHECK_URL)){
            filterChain.doFilter(request,response);
            return;
        }

        String refreshToken = jwtService.exportRefreshToken(request)
                //하단의 jwtService의 isTokenValid의 파라미터 어떤게 들어가는지 확인해볼것
                .filter(jwtService::isTokenValid)
                .orElse(null);

        /*
            Refresh Token이 존재하는 경우 AccessToken의 재발급 필요
            db의 refrshToken과 비교후 일치 시 AccessToken 재발급
        */
        if(refreshToken != null){
            checkRefreshTokenAndAccessToken(response,refreshToken);
        }
        /*
            Refresh Token이 없을경우 Access Token 유효성 체크후
            Access Token의 갱신
        */
        if(refreshToken == null) {
            checkAccessToken(request, response, filterChain);
        }
    }

    /**
     * refresh Token이 일치하는 계정이 있을경우
     * access Token 재발급 후 refresh, access Token  header에 전송
     */
    public void checkRefreshTokenAndAccessToken(HttpServletResponse response, String refreshToken){
        // refresh Token이 일치하는 계정이 있을경우
        // refreshToken 재발급 후 accessToken 재발급
        userRepository.findByRefreshToken(refreshToken)
                .ifPresent(user -> {
                    String newRefreshToken = newRefreshToken(user);
                    jwtService.sendAccessAndRefreshToken(response
                            ,jwtService.createAccsessToken(newRefreshToken)
                            ,newRefreshToken);
                });
    }

    /**
     *  Refresh Token 재발급 및 update
     * */
    private String newRefreshToken(User user){
        String newRefreshToken = jwtService.createRefreshToken();
        user.updateRefreshToken(newRefreshToken);
        userRepository.saveAndFlush(user);// 즉시 db에 반영
        return newRefreshToken;
    }

    /**
     * 응답에 refresh Token이 없는경우 access Token 유효성 체크 후인증 허가
     * FilterChain 이란 filter의 집합 - 연속된 filter의 집합
     *
     * */
    public void checkAccessToken(HttpServletRequest request
                , HttpServletResponse response
                , FilterChain filterChain) throws ServletException, IOException  {

        log.info("checkAccessToken () 호출");
        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                // access token 유효성 체크
                .ifPresent(accessToken ->
                        jwtService.exportEmail(accessToken).ifPresent(email ->
                                // email의 사용자 정보 갱신
                                userRepository.findByEmail(email).ifPresent(
                                    this::saveAuthentication
                                )
                        )
                );
        filterChain.doFilter(request,response);

    }

    public void saveAuthentication(User user){
        String password = user.getPassword();

        // oauth2로 로그인한 사용자의 비밀번호 랜덤으로 생성
        if(password == null){
            password = PasswordUtil.generateRandomPassword();
        }

        //UserDetails -> spring security에서 사용자의 정보를 담는 인터페이스
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getName())
                .password(password)
                .build();

        // spring security의 인증 정보 셋
        // Authentication - 현재 접근하는 주체의 정보와 권한을 담는 인터페이스
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails,null
                        ,authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
