package com.example.demo.controller;

import com.example.demo.DemoApplication;
import com.example.demo.model.User;
import com.example.demo.repository.UserJpaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
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

    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    private UserJpaRepository userJpaRepository;

    @ApiOperation(value = "Get All Users", notes = "Gets list of all users", response = Object.class, tags = {"Get Users"})
    @GetMapping(value = "/user")
    public List<User> findAll() {
        List<User> list = userJpaRepository.findAll();
        logger.info(String.valueOf(list));
        return list;
    }

    @ApiOperation(value = "Get User by Email", response = Object.class, tags = {"Get User by Email"})
    @GetMapping(value = "/user/{email}")
    public User findByEmail(@PathVariable String email) {
        logger.info(String.valueOf(email));
        return userJpaRepository.findByEmail(email);
    }

    @ApiOperation(value = "Save User", response = Object.class, tags = {"Create User"})
    @PostMapping(value = "/user")
    public User saveUser(@RequestBody User user) {
        logger.info(String.valueOf(user));
        boolean exists = userJpaRepository.existsByEmail(user.getEmail());
        logger.info("exist:" + exists);
        if (exists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "entity already exists");
        }
        return userJpaRepository.save(user);
    }
}
