package com.memnik.controller;

import com.memnik.common.config.SecurityConfig;
import com.memnik.controller.admin.AdminController;
import com.memnik.service.user.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.memnik.factory.UserFactory.USER_ID_PARAMS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
@Import({SecurityConfig.class, UserDetailsServiceImpl.class})
class AdminControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;
    private MockMvc mockMvc;
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUserOk() throws Exception {
        when(userDetailsService.deleteUser(any())).thenReturn(true);

        String result = mockMvc.perform(MockMvcRequestBuilders.post("/admin")
                        .params(USER_ID_PARAMS))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("true", result);
    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteUserFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin")
                        .params(USER_ID_PARAMS))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}