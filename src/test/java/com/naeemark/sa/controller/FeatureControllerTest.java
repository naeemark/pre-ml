package com.naeemark.sa.controller;

import com.naeemark.sa.repository.FeatureRepository;
import com.naeemark.sa.utils.TestDataFactory;
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
@WebMvcTest(FeatureController.class)
@DisplayName("Unit tests for FeatureController")
class FeatureControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeatureRepository repository;

    @Test
    @DisplayName("GET an empty list of Features")
    void testAllFeaturesWithNoRecords() throws Exception {
        //given
        when(repository.findAll()).thenReturn(Collections.emptyList());

        // when
        mockMvc.perform(get("/api/feature/all"))
                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    @DisplayName("GET a list with single Feature")
    void testAllFeaturesWithSingleRecords() throws Exception {
        //given
        when(repository.findAll()).thenReturn(Collections.singletonList(TestDataFactory.getSingleFeature(1, "abc")));

        mockMvc.perform(get("/api/feature/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].id").value("1"))
                .andExpect(jsonPath("[0].name").value("abc"));
    }

    @Test
    @DisplayName("GET Feature by Name")
    void testGetFeatureByName_Success() throws Exception {

        String name = "abc";

        //given
        when(repository.findByName(name)).thenReturn(TestDataFactory.getSingleFeature(1, name));

        mockMvc.perform(get("/api/feature/"+name))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    @DisplayName("GET Feature by Name with invalid param")
    void testGetUserByEmail_Failure() throws Exception {

        String name = "abc";

        //given
        when(repository.findByName(name)).thenReturn(null);

        mockMvc.perform(get("/api/user/123"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}