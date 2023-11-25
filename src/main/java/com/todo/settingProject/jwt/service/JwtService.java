package com.todo.settingProject.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.todo.settingProject.domain.repository.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    /**
     * JWT의 Subject와 Claim으로 email 사용 -> 클레임의 name을 "email"으로 설정
     * JWT의 헤더에 들어오는 값 : 'Authorization(Key) = Bearer {토큰} (Value)' 형식
     */
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";
    private static final String BEARER = "Bearer ";


    private final UserRepository userRepository;

    /**
     * AccsessToken 발급
     */
    public String createAccsessToken(String email) {
        Date now = new Date();
        return JWT.create() // JWT 토큰을 생성
                .withSubject(ACCESS_TOKEN_SUBJECT) // JWT의 Subject(토큰 제목) 지정-
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod)) // 토큰 만료 시간 설정
                .withClaim("email", email)  // jwt에 email 클레임 추가
                .sign(Algorithm.HMAC512(secretKey));// sign() -  암호화 알고리즘(암호화키) 지정시 암호화되어 jwt토큰 생성
    }

    /**
     * RefreshToken 발급
     */
    public String createRefreshToken(){
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(secretKey));
    }

    /**
     * AccessToken 헤더에 데이터 셋해서 전송
     */
    public void sendAccsessToken(HttpServletResponse response, String accsessToken){
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(accsessToken,accsessToken);

        log.info("재발급된 accsessToken 전송 : {} ",accsessToken);
    }

    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken){
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);

        log.info("재발급된 Access Token, Refresh Token 헤더 성정 완료");

    }

    /**
     * Header에서 Refresh Token 추출
     */
    public Optional<String> exportRefreshToken(HttpServletRequest request){
        log.info("exportRefreshToken - header 토큰 형식 출력 : {}",request.getHeader(refreshHeader));
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                // 토큰형식의 BEARER xxxx 형식에서 BEARER 제거 후 순수 Refresh Token 추출
                .map(refreshToken -> refreshToken.replace(BEARER,""));
    }

    /**
     * Header에서 Access Token 추출
     */
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                // 토큰형식의 BEARER xxxx 형식에서 BEARER 제거 후 순수 Access Token 추출
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    public Optional<String> exportEmail (String accessToken){
        try {
            // 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier builder 반환
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(accessToken) // accessToken을 검증하고 유효하지 않다면(Exception) 예외 발생
                    .getClaim(EMAIL_CLAIM) // claim(Emial) 가져오기
                    .asString());
        } catch (Exception e) {
            log.error("Access Token이 유효하지 않습니다");
            return Optional.empty();
        }
    }

    /**
     * AccessToken 헤더 설정
     */
    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(accessHeader, accessToken);
    }

    /**
     * RefreshToken 헤더 설정
     */
    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader(refreshHeader, refreshToken);
    }

    /**
     * email(사용자)의 Refresh Token update
     */
    public void updateRefeshToken(String email, String refreshToken){
        userRepository.findByEmail(email)
                .ifPresentOrElse(
                        user -> {
                            user.updateRefreshToken(refreshToken);
                            userRepository.save(user);
                        },
                        ()-> new Exception("일치하는 회원이 없습니다.")
                );
        log.info("refresh token update");
    }

    /**
     * 토큰의 유효성 체크
     */
    public boolean isTokenValid(String token){
        try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않는 토큰입니다. {}",e.getMessage());
            return false;
        }
    }


}
