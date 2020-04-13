package com.naeemark.sa.controller;

import com.naeemark.sa.SwitchAccessApplication;
import com.naeemark.sa.model.Feature;
import com.naeemark.sa.repository.FeatureRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    private static final Logger logger = LoggerFactory.getLogger(SwitchAccessApplication.class);

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
    @GetMapping(value = "/feature/{featureName}")
    public Feature findByName(@PathVariable String featureName) {

        logger.info(featureName);
        Feature feature = featureRepository.findByName(featureName);
        if (feature == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested data not found");
        return feature;
    }
}
