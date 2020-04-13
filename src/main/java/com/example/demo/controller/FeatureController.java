package com.example.demo.controller;

import com.example.demo.DemoApplication;
import com.example.demo.model.Feature;
import com.example.demo.model.User;
import com.example.demo.repository.FeatureRepository;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Created by Naeem <naeemark@gmail.com>.
 * <p>
 * Created on: 2020-04-11
 */
@Api(tags = "Retrieve Features")
@RestController
@RequestMapping("/api")
public class FeatureController {

    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    private FeatureRepository featureRepository;


    @ApiOperation(value = "Get All Features", notes = "Gets list of all features", response = Feature.class, responseContainer = "List", tags = {"Retrieve Features"})
    @GetMapping(value = "/feature/all")
    public List<Feature> findAll() {

        List<Feature> list = featureRepository.findAll();
        logger.info(String.valueOf(list));
        return list;
    }

    @ApiOperation(value = "Get Feature by Name", response = Feature.class, tags = {"Retrieve Features"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Validation Error")
    })
    @GetMapping(value = "/feature/{featureName}")
    public Feature findByEmail(@PathVariable String featureName) {

        if (Strings.isNullOrEmpty(featureName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Validation Error");
        }

        logger.info(featureName);
        Feature feature = featureRepository.findByName(featureName);
        if (feature == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested data not found");
        return feature;
    }
}
