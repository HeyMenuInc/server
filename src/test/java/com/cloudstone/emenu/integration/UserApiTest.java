package com.cloudstone.emenu.integration;

/**
 * Created by charliez on 4/20/14.
 */
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserApiTest extends TestBase {

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