package com.naeemark.sa.controller;

import com.naeemark.sa.SwitchAccessApplication;
import com.naeemark.sa.model.AccessRequest;
import com.naeemark.sa.utils.TestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Naeem <naeemark@gmail.com>.
 * <p>
 * Created on: 2020-04-13
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SwitchAccessApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@DisplayName("Integration tests for AccessController")
class AccessControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    String email = "abc@gmail.com";
    String featureName = "ABC";

    @Test
    @DisplayName("GET Feature Access")
    void testFeatureAccess_Success() throws Exception {

        String email = "abc@gmail.com";
        String featureName = "ABC";

        // when
        mockMvc.perform(get("/feature").queryParam("email", email).queryParam("featureName", featureName))
                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.canAccess").value(false));
    }


    @Test
    @DisplayName("POST Feature Access Update Enable=True")
    void testFeatureAccessUpdateEnableTrue() throws Exception {

        AccessRequest accessRequest = TestDataFactory.getAccessRequest(email, featureName, true);
        mockMvc.perform(post("/feature")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestDataFactory.asJsonString(accessRequest))
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}