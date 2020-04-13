package com.example.demo.controller;

import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.example.demo.utils.TestDataFactory.getSingleUser;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Naeem <naeemark@gmail.com>.
 * <p>
 * Created on: 2020-04-13
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@DisplayName("Unit tests for UserController")
class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository repository;

    @Test
    @DisplayName("GET an empty list of Users")
    void testAllUsersWithNoRecords() throws Exception {
        //given
        when(repository.findAll()).thenReturn(Collections.emptyList());

        // when
        mockMvc.perform(get("/api/user/all"))
                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    @DisplayName("GET a list with single User")
    void testAllUsersWithSingleRecords() throws Exception {
        //given
        when(repository.findAll()).thenReturn(Collections.singletonList(getSingleUser(1, "abc@gmail.com")));

        mockMvc.perform(get("/api/user/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].id").value("1"))
                .andExpect(jsonPath("[0].email").value("abc@gmail.com"));
    }

    @Test
    @DisplayName("GET User by Email")
    void testGetUserByEmail_Success() throws Exception {

        String email = "abc@gmail.com";

        //given
        when(repository.findByEmail(email)).thenReturn(getSingleUser(1, email));

        mockMvc.perform(get("/api/user/"+email))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.email").value(email));
    }


    @Test
    @DisplayName("GET User by Email Not Found")
    void testGetUserByEmail_NotFound() throws Exception {

        String email = "abc@gmail.com";

        //given
        when(repository.findByEmail(email)).thenReturn(null);

        mockMvc.perform(get("/api/user/"+email))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("GET User by Email with invalid param")
    void testGetUserByEmail_Failure() throws Exception {

        String email = "abc@gmail.com";

        //given
        when(repository.findByEmail(email)).thenReturn(getSingleUser(1, email));

        mockMvc.perform(get("/api/user/abc.123"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}