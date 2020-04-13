package com.example.demo.controller;

import com.example.demo.model.AccessRequest;
import com.example.demo.model.AccessResponse;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

/**
 * Functional Requirement
 *
 * Created by Naeem <naeemark@gmail.com>.
 * <p>
 * Created on: 2020-04-13
 */
@Api
@RestController
@RequestMapping("/feature")
public class AccessController {

    private static final Logger logger = LoggerFactory.getLogger(AccessController.class);

    @GetMapping
    public AccessResponse retrieveFeatureAccess(@RequestParam String email, @RequestParam String featureName){
        return new AccessResponse(new Random().nextBoolean());
    }

    @PostMapping
    public AccessRequest updateFeatureAccess(@RequestBody AccessRequest accessRequest){

//        logger.info(accessRequest.toString());
//
//        User user = userRepository.findByEmail(accessRequest.getEmail());
//        if(user == null){
//            user = new User(accessRequest.getEmail());
//        }
//
//        logger.info(user.toString());
//
//        Feature feature = featureRepository.findByName(accessRequest.getFeatureName());
//        if(feature == null)
//            feature = new Feature(accessRequest.getFeatureName());
//
//        logger.info(feature.toString());
//
//        user.getFeatures().add(feature);
//        userRepository.save(user);
//

        return accessRequest;
    }
}
