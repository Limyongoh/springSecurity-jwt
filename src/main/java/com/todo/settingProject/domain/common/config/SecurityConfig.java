package com.todo.settingProject.domain.common.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.settingProject.domain.repository.user.UserRepository;
import com.todo.settingProject.jwt.filter.JwtFilter;
import com.todo.settingProject.jwt.service.JwtService;
import com.todo.settingProject.login.CustomJsonUsernamePasswordFiter;
import com.todo.settingProject.login.handler.LoginFailHandler;
import com.todo.settingProject.login.handler.LoginSuccessHandler;
import com.todo.settingProject.login.service.LoginService;
import com.todo.settingProject.oauth.handler.Oauth2LoginFailureHandler;
import com.todo.settingProject.oauth.handler.Oauth2LoginSuccessHandler;
import com.todo.settingProject.oauth.service.OauthCustom;
import com.todo.settingProject.NoSecurity_Oauth.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final OauthService oauthService;
    private  final OauthCustom oauthCustom;

    private final LoginService loginService;

    private final JwtService jwtService;
    private final UserRepository userRepository;

    private final Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;
    private final Oauth2LoginFailureHandler oauth2LoginFailureHandler;

    @Bean
    // 비밀번호 암호화
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
            // 쿠키에 의존하는 것이 않고 oauth2.0이나 jwt를 사용하는 Rest api의 경우 csrf보호 필요없음
            .csrf(AbstractHttpConfigurer::disable)

            // basic 인증방식은 username:password를 base64 인코딩으로 Authroization 헤더로 보내는 방식
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .headers(AbstractHttpConfigurer::disable)
            .sessionManagement(sessoin->
                    sessoin.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        // url 별로 권한 설정관리 가능
        http.authorizeHttpRequests(auth->
            auth
                .requestMatchers("/","/css/**","/images/**","/js/**","/favicon.ico","/h2-console/**").permitAll()
                .requestMatchers("/sign-up").permitAll() // 회원가입 접근 가능
                .anyRequest().authenticated() // 위의 경로 이외에는 모두 인증된 사용자만 접근 가능
        );

        // oauth2 로그인 관련설정
        http.oauth2Login(oauthLogin->
            oauthLogin
                    .userInfoEndpoint(endPoint->
                            endPoint.userService(oauthCustom))
                    // 위의 oauthCustom 수행후 로그인 성공 시 custom handler 수행
                    .successHandler(oauth2LoginSuccessHandler)
                    // 위의 oauthCustom 수행후 로그인 실패시 시 custom handler 수행
                    .failureHandler(oauth2LoginFailureHandler)
        );


        return http.build();

        }

    @Bean
    public AuthenticationManager authentication(){
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setPasswordEncoder(passwordEncoder());
            provider.setUserDetailsService(loginService);
            return new ProviderManager(provider);
        }
        @Bean
        public JwtFilter jwtAuthenticationProcessingFilter() {
            JwtFilter jwtAuthenticationFilter = new JwtFilter(jwtService, userRepository);
            return jwtAuthenticationFilter;
        }


    /**
     * 로그인 성공 시 호출되는 LoginSuccessJWTProviderHandler 빈 등록
     */
    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtService, userRepository);
    }

    /**
     * 로그인 실패 시 호출되는 LoginFailureHandler 빈 등록
     */
    @Bean
    public LoginFailHandler loginFailureHandler() {
        return new LoginFailHandler();
    }


    @Bean
    public CustomJsonUsernamePasswordFiter customJsonUsernamePasswordAuthenticationFilter(){
            CustomJsonUsernamePasswordFiter customJsonUsernamePasswordFiter = new CustomJsonUsernamePasswordFiter(objectMapper);
            customJsonUsernamePasswordFiter.setAuthenticationManager(authentication());
            customJsonUsernamePasswordFiter.setAuthenticationSuccessHandler(loginSuccessHandler());
            customJsonUsernamePasswordFiter.setAuthenticationFailureHandler(loginFailureHandler());
            return customJsonUsernamePasswordFiter;
        }


}