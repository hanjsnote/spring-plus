package org.example.expert.domain.auth;

import org.example.expert.config.JwtUtil;
import org.example.expert.config.SecurityConfig;
import org.example.expert.config.WithMockAuthUser;
import org.example.expert.domain.auth.controller.TestController;
import org.example.expert.domain.user.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
@Import({SecurityConfig.class, JwtUtil.class})
public class TestControllerWithMockAuthUserTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockAuthUser(userId = 1L, email = "admin@test.com", role = UserRole.ROLE_ADMIN, nickname = "ADMIN")
    public void 권한이_ADMIN일_경우_200() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockAuthUser(userId = 1L, email = "user@test.com", role = UserRole.ROLE_USER, nickname = "USER")
    public void 권한이_USER일_경우_403() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void 토큰이_없을_경우() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isForbidden());
    }
}
