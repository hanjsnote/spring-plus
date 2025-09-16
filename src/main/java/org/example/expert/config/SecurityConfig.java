package org.example.expert.config;


import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity  //Spring Security 활성화
@EnableMethodSecurity(securedEnabled = true)    // @Secured 같은 메서드 단위 권한 제어 활성화
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){   //회원가입 시 비밀번호를 BCrypt 해시 알고리즘으로 암호화 빈으로 등록하여 로그인 과정에서 같은 인코더 사용
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)  //REST API에서는 보통 JWT를 쓰기 때문 CSRF 토큰 불필요
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)     //세션을 전혀 쓰지 않고 매 요청마다 JWT로 인증
                )
                //커스텀 JWT 필터 UsernamePasswordAuthenticationFilter 같은 기본 인증 필터 대신 jwtAuthenticationFilter가 들어와서 JWT 검증
                .addFilterBefore(jwtAuthenticationFilter, SecurityContextHolderAwareRequestFilter.class)

                //JWT 사용 시 불필요한 기능들
//                .formLogin(AbstractHttpConfigurer::disable)     //[SSR] 서버가 로그인 HTML 폼 렌더링
//                .anonymous(AbstractHttpConfigurer::disable)     //미인증 사용자를 익명으로 처리
//                .httpBasic(AbstractHttpConfigurer::disable)     //[SSR] 인증 팝업
//                .logout(AbstractHttpConfigurer::disable)        //[SSR] 서버가 세션 무효화 후 리다이렉트
//                .rememberMe(AbstractHttpConfigurer::disable)    //서버가 쿠키 발급하여 자동 로그인

                //요청별 권한 제어
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(request -> request.getRequestURI().startsWith("/auth")).permitAll()
//                        .requestMatchers("/test").hasAuthority(UserRole.Authority.ADMIN)    // /test는 ADMIN만 허용
                        .requestMatchers("/open").permitAll()       // /open은 아무나 접근 가능
                        .anyRequest().authenticated() //SecurityContext에 AbstractAuthenticationToken이 set이 되어있다면 통과
                )
                .build();
    }
}
