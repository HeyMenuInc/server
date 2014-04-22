package com.cloudstone.emenu.integration;

/**
 * Created by charliez on 4/20/14.
 */
import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.logic.UserLogic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:integrationTestContext.xml", "classpath:commonContext.xml"})
@WebAppConfiguration
public class UserApiIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void login() throws Exception {
        mockMvc.perform(post("/api/login")
                .param("name", "admin").param("password", "admin"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/login")
                .param("name", "admin").param("password", "randomStuff"))
                .andExpect(status().isUnauthorized());
    }
}