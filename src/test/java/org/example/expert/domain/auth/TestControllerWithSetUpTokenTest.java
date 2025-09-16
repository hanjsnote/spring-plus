package org.example.expert.domain.auth;

import org.example.expert.config.JwtAuthenticationToken;
import org.example.expert.config.JwtUtil;
import org.example.expert.config.SecurityConfig;
import org.example.expert.domain.auth.controller.TestController;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
@Import({SecurityConfig.class, JwtUtil.class})
public class TestControllerWithSetUpTokenTest {

    @Autowired
    private MockMvc mockMvc;

    private JwtAuthenticationToken adminAuthenticationToken;
    private JwtAuthenticationToken userAuthenticationToken;

    @BeforeEach
    public void setUp() {
        AuthUser adminUser = new AuthUser(1L, "admin@test.com", UserRole.ROLE_ADMIN, "ADMIN");
        adminAuthenticationToken = new JwtAuthenticationToken(adminUser, adminUser.getAuthorities());

        AuthUser normalUser = new AuthUser(2L, "user@test.com", UserRole.ROLE_USER, "USER");
        userAuthenticationToken = new JwtAuthenticationToken(normalUser, normalUser.getAuthorities());
    }

    @Test
    public void 권한이_ADMIN일_경우_200() throws Exception {
        mockMvc.perform(get("/test")
                        .with(authentication(adminAuthenticationToken)))
                .andExpect(status().isOk());
    }

    @Test
    public void 권한이_USER일_경우_403() throws Exception {
        mockMvc.perform(get("/test")
                        .with(authentication(userAuthenticationToken)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void 토큰이_없을_경우() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isForbidden());
    }

}
