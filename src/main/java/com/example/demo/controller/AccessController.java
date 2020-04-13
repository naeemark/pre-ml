package com.example.demo.controller;

import com.example.demo.model.AccessRequest;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public AccessRequest retrieveFeatureAccess(@RequestParam String email, @RequestParam String featureName){
        return new AccessRequest(email, featureName, true);
    }

    @PostMapping
    public AccessRequest updateFeatureAccess(@RequestBody AccessRequest accessRequest){
        return accessRequest;
    }
}
