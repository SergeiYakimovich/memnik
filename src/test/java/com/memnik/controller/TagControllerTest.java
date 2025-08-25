package com.memnik.controller;

import com.memnik.common.CurrentUserResolver;
import com.memnik.common.config.SecurityConfig;
import com.memnik.service.TagService;
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

import static com.memnik.common.constants.Constants.TAG_URL;
import static com.memnik.factory.UserFactory.USER_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TagController.class)
@Import({SecurityConfig.class, UserDetailsServiceImpl.class})
public class TagControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;
    @MockBean
    private TagService tagService;
    @MockBean
    private CurrentUserResolver currentUserResolver;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
        when(currentUserResolver.getCurrentUser()).thenReturn(USER_DTO);
    }

    @Test
    @WithMockUser(roles = "USER")
    void getTagsOK() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/"+TAG_URL))
                .andDo(print())
                .andReturn()
                .getModelAndView().getModelMap()
                .get("title").toString();

        assertEquals("Existing tags", result);
    }

    @Test
    void getMemsFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"+TAG_URL))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "USER")
    void createTagOK() throws Exception {
        when(tagService.createTag(any(), any())).thenReturn(true);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/"+TAG_URL)
                        .param("name", "test"))
                .andDo(print())
                .andReturn()
                .getModelAndView().getModelMap()
                .get("error").toString();

        assertEquals("Tag with name test has been created", result);
    }

    @Test
    @WithMockUser(roles = "USER")
    void createTagFail() throws Exception {
        when(tagService.createTag(any(), any())).thenReturn(false);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/"+TAG_URL)
                        .param("name", "test"))
                .andDo(print())
                .andReturn()
                .getModelAndView().getModelMap()
                .get("error").toString();

        assertEquals("Tag with name test already exists", result);
    }
}
