package org.example.expert.domain.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @Secured(UserRole.Authority.ADMIN)
    @GetMapping("/test")
    public void test(@AuthenticationPrincipal AuthUser authUser){
        log.info("User ID: {}", authUser.getId());
        log.info("Email: {}", authUser.getEmail());
        log.info("UserRole: {}", authUser.getUserRole());
        log.info("nickname{}", authUser.getNickname());
    }

    @GetMapping("/open")
    public void open(){
    }
}
