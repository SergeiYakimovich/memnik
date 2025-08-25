package com.memnik.controller;

import com.memnik.common.CurrentUserResolver;
import com.memnik.common.LanguageResolver;
import com.memnik.common.config.SecurityConfig;
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

import static com.memnik.factory.UserFactory.USER_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class)
@Import({SecurityConfig.class, UserDetailsServiceImpl.class, LanguageResolver.class})
public class HomeControllerTest {
    @Autowired
    private HomeController homeController;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;
    @MockBean
    private LanguageResolver languageResolver;
    @MockBean
    private CurrentUserResolver currentUserResolver;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");
        mockMvc = MockMvcBuilders
                .standaloneSetup(homeController)
                .setViewResolvers(viewResolver)
                .build();
        when(currentUserResolver.getCurrentUser()).thenReturn(USER_DTO);
        when(languageResolver.resolve(any())).thenReturn("Hello");
    }

    @Test
    void helloOkTest() throws Exception {
        String title = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getModelAndView().getModelMap()
                .get("title").toString();

        assertEquals(title, "Hello");
    }

    @Test
    void languageOkTest() throws Exception {
        String title = mockMvc.perform(MockMvcRequestBuilders.post("/?language=RU"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andReturn()
                .getResponse().getRedirectedUrl();

        assertEquals(title, "/");
    }

}
