package org.example.expert.domain.auth;

import org.example.expert.config.JwtAuthenticationToken;
import org.example.expert.config.JwtUtil;
import org.example.expert.config.SecurityConfig;
import org.example.expert.domain.auth.controller.TestController;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
@Import({SecurityConfig.class, JwtUtil.class})
public class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 권한이_ADMIN일_경우_성공() throws Exception {
        AuthUser authUser = new AuthUser(1L, "admin@test.com", UserRole.ROLE_ADMIN, "ADMIN");

        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(authUser, null);

        mockMvc.perform(get("/test")
                        .with(authentication(authenticationToken)))
                .andExpect(status().isOk());
    }








}
