package com.example.demo.controller;

import com.example.demo.DemoApplication;
import com.example.demo.model.Feature;
import com.example.demo.repository.FeatureRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Naeem <naeemark@gmail.com>.
 * <p>
 * Created on: 2020-04-11
 */
@Api
@RestController
@RequestMapping("/api")
public class FeatureController {

    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    private FeatureRepository featureRepository;


    @ApiOperation(value = "Get All Features", notes = "Gets list of all features", response = Object.class, tags = {"Get Features"})
    @GetMapping(value = "/feature/all")
    public List<Feature> findAll() {
        List<Feature> list = featureRepository.findAll();
        logger.info(String.valueOf(list));
        return list;
    }
}
