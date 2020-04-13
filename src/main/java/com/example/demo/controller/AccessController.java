package com.example.demo.controller;

import com.example.demo.model.AccessRequest;
import com.example.demo.model.AccessResponse;
import com.example.demo.model.Feature;
import com.example.demo.model.User;
import com.example.demo.repository.FeatureRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Utils;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Random;

/**
 * Functional Requirement
 * <p>
 * Created by Naeem <naeemark@gmail.com>.
 * <p>
 * Created on: 2020-04-13
 */
@Api(tags = "Access Retrieve / Update", description = "Operations related to Access Management - Functional Requirement")
@RestController
@RequestMapping("/feature")
public class AccessController {

    private static final Logger logger = LoggerFactory.getLogger(AccessController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeatureRepository featureRepository;

    /**
     * Sample Request Params
     * /feature?email=abc@gmail.com&featureName=A
     *
     * @param email
     * @param featureName
     * @return
     */
    @ApiOperation(value = "Retrieve Feature Access", response = AccessResponse.class, tags = {"Access Retrieve / Update"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Validation Error")
    })
    @GetMapping
    public AccessResponse retrieveFeatureAccess(@RequestParam String email, @RequestParam String featureName) {

        if (!Utils.isValidEmail(email) || Strings.isNullOrEmpty(featureName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Validation Error");
        }

        User user = userRepository.findByEmail(email);
        Feature feature = featureRepository.findByName(featureName);

        boolean responseFlag = false;
        if (user != null && feature != null && user.getFeatures().contains(feature))
            responseFlag = true;
        return new AccessResponse(responseFlag);
    }

    /**
     * Sample Request Params
     * {
     * "email": "abcd@gmail.com",
     * "featureName": "A",
     * "enable": true
     * }
     *
     * @param accessRequest
     */
    @ApiOperation(value = "Update Feature Access", tags = {"Access Retrieve / Update"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 304, message = "Update Request was not successful"),
            @ApiResponse(code = 400, message = "Validation Error")
    })
    @PostMapping
    public void updateFeatureAccess(@Valid @RequestBody AccessRequest accessRequest) {

        logger.info(accessRequest.toString());

        if (accessRequest.isEnable()) {
            User user = userRepository.findByEmail(accessRequest.getEmail());
            if (user == null) {
                user = new User(accessRequest.getEmail());
            }
            logger.info(user.toString());

            Feature feature = featureRepository.findByName(accessRequest.getFeatureName());
            if (feature == null) {
                feature = new Feature(accessRequest.getFeatureName());
            }
            logger.info(feature.toString());

            if (!user.getFeatures().contains(feature)) {
                user.getFeatures().add(feature);
                userRepository.save(user);
                return;
            }
        } else {
            User user = userRepository.findByEmail(accessRequest.getEmail());
            Feature feature = featureRepository.findByName(accessRequest.getFeatureName());
            if (user != null && feature != null && user.getFeatures().contains(feature)) {
                user.getFeatures().remove(feature);
                userRepository.save(user);
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "Update Request was not successful");
    }
}
