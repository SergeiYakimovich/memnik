package com.memnik.controller;

import com.memnik.common.LanguageResolver;
import com.memnik.common.config.SecurityConfig;
import com.memnik.controller.registration.RegistrationController;
import com.memnik.service.user.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static com.memnik.factory.UserFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistrationController.class)
@Import({SecurityConfig.class, UserDetailsServiceImpl.class})
class RegistrationControllerTest {
    @Autowired
    private RegistrationController registrationController;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;
    @MockBean
    private LanguageResolver languageResolver;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");
        mockMvc = MockMvcBuilders
                .standaloneSetup(registrationController)
                .setViewResolvers(viewResolver)
                .build();
        when(languageResolver.resolve(any())).thenReturn("Hello");
    }

    @Test
    void registrationOkTest() throws Exception {
        when(userDetailsService.createUser(any())).thenReturn(true);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/registration")
                        .params(REGISTRATION_PARAMS))
                .andDo(print())
                .andReturn();

        assertThat(mvcResult.getModelAndView().getViewName()).isEqualTo("main");
    }

    @Test
    void registrationFailUsernameTest() throws Exception {
        when(userDetailsService.createUser(any())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/registration")
                        .params(REGISTRATION_PARAMS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("error", "User with name %s already exists".formatted(USER_NAME)))
                .andExpect(view().name("registration"));
    }

    @Test
    void registrationFailPasswordTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/registration")
                        .params(REGISTRATION_PARAMS_WRONG_RASSWORD))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("error", "Passwords must be equal"))
                .andExpect(view().name("registration"));
    }

    @Test
    void registrationFailBindingsTest() throws Exception {
        String error = mockMvc.perform(MockMvcRequestBuilders.post("/registration")
                        .params(REGISTRATION_WRONG_PARAMS))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getModelAndView().getModelMap()
                .get("error").toString();

        assertTrue(error.contains("passwordConfirm: must not be blank"));
        assertTrue(error.contains("password: size must be between 3 and 20"));
        assertTrue(error.contains("name: must not be blank"));
    }
}