package com.memnik.controller;

import com.memnik.common.LanguageResolver;
import com.memnik.common.config.SecurityConfig;
import com.memnik.controller.registration.UnsubscribeController;
import com.memnik.service.user.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UnsubscribeController.class)
@Import({SecurityConfig.class, UserDetailsServiceImpl.class})
public class UnsubscribeControllerTest {
    @Autowired
    private UnsubscribeController unsubscribeController;
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
                .standaloneSetup(unsubscribeController)
                .setViewResolvers(viewResolver)
                .build();
        when(languageResolver.resolve(any())).thenReturn("Hello");
    }

    @Test
    void unsubscribeOkTest() throws Exception {
        when(userDetailsService.unsubscribeUser(any())).thenReturn(true);
        String title = mockMvc.perform(MockMvcRequestBuilders.get("/unsubscribe/1234"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getModelAndView().getModelMap()
                .get("title").toString();

        assertEquals(title, "Unsubscribing from emails");
    }
}
