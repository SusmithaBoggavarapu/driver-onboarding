package com.uber.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uber.entity.user.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createRole() {
        try {
            mockMvc.perform(post("/user/createRole")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(Role.builder().roleName("User").roleDescription("default role").build())))
                    .andExpect(status().isOk());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
