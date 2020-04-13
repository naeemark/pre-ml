package com.example.demo.controller;

import com.example.demo.model.AccessRequest;
import com.example.demo.model.Feature;
import com.example.demo.model.User;
import com.example.demo.repository.FeatureRepository;
import com.example.demo.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Created by Naeem <naeemark@gmail.com>.
 * <p>
 * Created on: 2020-04-11
 */
@Api
@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeatureRepository featureRepository;


    @ApiOperation(value = "Get All Users", notes = "Gets list of all users", response = Object.class, tags = {"Get Users"})
    @GetMapping(value = "/user/all")
    public List<User> findAll() {
        List<User> list = userRepository.findAll();
        logger.info(String.valueOf(list));
        return list;
    }

    @ApiOperation(value = "Get User by Email", response = Object.class, tags = {"Get User by Email"})
    @GetMapping(value = "/user/{email}")
    public User findByEmail(@PathVariable String email) {
        logger.info(String.valueOf(email));
        return userRepository.findByEmail(email);
    }

    @ApiOperation(value = "Save User", response = Object.class, tags = {"Create User"})
    @PostMapping(value = "/user")
    public User saveUser(@RequestBody User user) {
        logger.info(String.valueOf(user));
        boolean exists = userRepository.existsByEmail(user.getEmail());
        logger.info("exist:" + exists);
        if (exists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "entity already exists");
        }
        return userRepository.save(user);
    }


    @PostMapping(value = "/test")
    /**
     * Sample Request Body
     *  {
     * 	"email": "abcd@gmail.com",
     * 	"featureName": "C",
     * 	"enable": true
     *  }
     *
     */
    public void save(@RequestBody AccessRequest accessRequest) {
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
        throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "Request update was not successful");
    }
}
