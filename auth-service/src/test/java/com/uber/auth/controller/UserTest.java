package com.uber.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uber.auth.entity.UserDto;
import com.uber.auth.entity.jwt.JwtResponse;
import com.uber.entity.user.User;
import com.uber.repository.user.UserJpaRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserJpaRepository userJpaRepository;

    private static final String USER_FILE = "src/test/resources/user.json";

    @Test
    public void register() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserDto userDto = getUser();
            mockMvc.perform(post("/user/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(userDto)))
                    .andExpect(status().isOk());

            User user = userJpaRepository.findById(userDto.getUsername()).get();
            Assert.assertEquals(userDto.getUsername(), user.getUsername());
            Assert.assertEquals(1, user.getRole().size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void validateUser() {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserDto userDto = getUser();
            mockMvc.perform(post("/user/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userDto)));

            String response = mockMvc.perform(post("/user/authenticate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userDto))).andReturn().getResponse().getContentAsString();

            System.out.println(response);
            JwtResponse jwtResponse = objectMapper.readValue(response, JwtResponse.class);


            mockMvc.perform(get("/validateUser")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtResponse.getJwtToken())).andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private UserDto getUser() {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new File(USER_FILE), UserDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
