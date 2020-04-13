package com.example.demo.controller;

import com.example.demo.model.AccessRequest;
import com.example.demo.model.Feature;
import com.example.demo.model.User;
import com.example.demo.repository.FeatureRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.TestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.demo.utils.TestDataFactory.*;
import static org.mockito.Mockito.when;
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
@WebMvcTest(AccessController.class)
@DisplayName("Unit tests for AccessController")
class AccessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private FeatureRepository featureRepository;


    String email = "abc@gmail.com";
    String featureName = "ABC";

    @Test
    @DisplayName("GET Feature Access")
    void testFeatureAccess_Success() throws Exception {

        String email = "abc@gmail.com";
        String featureName = "ABC";

        User user = getSingleUser(1, email);
        Feature feature = getSingleFeature(1, featureName);
        user.getFeatures().add(feature);

        //given
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(featureRepository.findByName(featureName)).thenReturn(feature);

        // when
        mockMvc.perform(get("/feature").queryParam("email", email).queryParam("featureName", featureName))
                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.canAccess").value(true));
    }


    @Test
    @DisplayName("GET Feature Access - Failed")
    void testFeatureAccess_withoutFeatureBind() throws Exception {
        //given
        when(userRepository.findByEmail(email)).thenReturn(getSingleUser(1, email));
        when(featureRepository.findByName(featureName)).thenReturn(getSingleFeature(1, featureName));

        // when
        mockMvc.perform(get("/feature").queryParam("email", email).queryParam("featureName", featureName))
                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.canAccess").value(false));
    }

    @Test
    @DisplayName("GET Feature Access - Invalid Param Email,")
    void testFeatureAccess_withoutParamEmail() throws Exception {

        //given
        when(userRepository.findByEmail(email)).thenReturn(getSingleUser(1, email));
        when(featureRepository.findByName(featureName)).thenReturn(getSingleFeature(1, featureName));

        // when
        mockMvc.perform(get("/feature").queryParam("featureName", featureName))
                // then
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET Feature Access - Invalid Param FeatureName,")
    void testFeatureAccess_withoutParamFeatureName() throws Exception {

        //given
        when(userRepository.findByEmail(email)).thenReturn(getSingleUser(1, email));
        when(featureRepository.findByName(featureName)).thenReturn(getSingleFeature(1, featureName));

        // when
        mockMvc.perform(get("/feature").queryParam("email", email))
                // then
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST Feature Access Update Enable=True")
    public void testFeatureAccessUpdateEnableTrue() throws Exception {
        //given
        AccessRequest accessRequest = getAccessRequest(email, featureName, true);
        //given
        when(userRepository.findByEmail(email)).thenReturn(getSingleUser(1, email));
        when(featureRepository.findByName(featureName)).thenReturn(getSingleFeature(1, featureName));

        //when
        mockMvc.perform(post("/feature")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestDataFactory.asJsonString(accessRequest))
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST Feature Access Update Enable=True New Record")
    public void testFeatureAccessUpdateEnableTrueNewRecord() throws Exception {
        //given
        AccessRequest accessRequest = getAccessRequest(email, featureName, true);
        //given
        when(userRepository.findByEmail(email)).thenReturn(null);
        when(featureRepository.findByName(featureName)).thenReturn(null);

        //when
        mockMvc.perform(post("/feature")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestDataFactory.asJsonString(accessRequest))
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST Feature Access Update Enable=True - Existing Access")
    public void testFeatureAccessUpdateEnableTrueExistingAccess() throws Exception {
        //given
        AccessRequest accessRequest = getAccessRequest(email, featureName, true);
        User user = getSingleUser(1, email);
        Feature feature = getSingleFeature(1, featureName);
        user.getFeatures().add(feature);

        //given
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(featureRepository.findByName(featureName)).thenReturn(feature);

        //when
        mockMvc.perform(post("/feature")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestDataFactory.asJsonString(accessRequest))
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isNotModified());
    }

    @Test
    @DisplayName("POST Feature Access Update Enable=False")
    public void testFeatureAccessUpdateEnableFalse() throws Exception {
        //given
        AccessRequest accessRequest = getAccessRequest(email, featureName, false);
        //given
        when(userRepository.findByEmail(email)).thenReturn(getSingleUser(1, email));
        when(featureRepository.findByName(featureName)).thenReturn(getSingleFeature(1, featureName));

        //when
        mockMvc.perform(post("/feature")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestDataFactory.asJsonString(accessRequest))
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isNotModified());
    }


    @Test
    @DisplayName("POST Feature Access Update Enable=False - Existing Access")
    public void testFeatureAccessUpdateEnableFalseExistingAccess() throws Exception {
        //given
        AccessRequest accessRequest = getAccessRequest(email, featureName, false);
        User user = getSingleUser(1, email);
        Feature feature = getSingleFeature(1, featureName);
        user.getFeatures().add(feature);

        //given
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(featureRepository.findByName(featureName)).thenReturn(feature);

        //when
        mockMvc.perform(post("/feature")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestDataFactory.asJsonString(accessRequest))
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST Feature Access Update Invalid param")
    public void testFeatureAccessUpdateInvalidParam() throws Exception {
        //given
        AccessRequest accessRequest = getAccessRequest("abc", featureName, false);
        User user = getSingleUser(1, email);
        Feature feature = getSingleFeature(1, featureName);
        user.getFeatures().add(feature);

        //given
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(featureRepository.findByName(featureName)).thenReturn(feature);

        //when
        mockMvc.perform(post("/feature")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestDataFactory.asJsonString(accessRequest))
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST Feature Access Update Invalid param NUll")
    public void testFeatureAccessUpdateInvalidParamNull() throws Exception {
        //given
        AccessRequest accessRequest = getAccessRequest("abc", null, false);
        User user = getSingleUser(1, email);
        Feature feature = getSingleFeature(1, featureName);
        user.getFeatures().add(feature);

        //given
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(featureRepository.findByName(featureName)).thenReturn(feature);

        //when
        mockMvc.perform(post("/feature")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestDataFactory.asJsonString(accessRequest))
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}