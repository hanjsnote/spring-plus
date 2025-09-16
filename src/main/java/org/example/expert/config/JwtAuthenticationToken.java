package org.example.expert.config;

import org.example.expert.domain.common.dto.AuthUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final AuthUser authUser;    //JWT안에 들어있는 사용자 정보(id, email, role, nickname)를 담은 도메인 객체

    //인증된 JWT 토큰을 생성하는 생성자, 권한과 인증 상태 세팅
    public JwtAuthenticationToken(AuthUser authUser, Collection<? extends GrantedAuthority> authorities){
        super(authorities);
        this.authUser = authUser;
        setAuthenticated(true); //Spring Security에 사용자가 이미 인증되었음을 알림
    }

    //JWT 인증에서는 토큰 검증 후 자격 증명이 필요하지 않으므로 null을 반환
    @Override
    public Object getCredentials() {
        return null;
    }

    //Principal(인증된 사용자)을 반환. (애플리케이션 전체에서 현재 사용자의 정보에 접근하는데 사용
    @Override
    public Object getPrincipal() {
        return authUser;
    }
}




