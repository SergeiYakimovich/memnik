package com.memnik.controller;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactsController.class)
@Import({SecurityConfig.class, UserDetailsServiceImpl.class})
public class ContactsControllerTest {
    @Autowired
    private ContactsController contactsController;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");
        mockMvc = MockMvcBuilders
                .standaloneSetup(contactsController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    void showContactsOkTest() throws Exception {
        String title = mockMvc.perform(MockMvcRequestBuilders.get("/contacts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getModelAndView().getModelMap()
                .get("title").toString();

        assertEquals(title, "Contacts");
    }
}
