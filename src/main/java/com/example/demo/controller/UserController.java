package com.example.demo.controller;

import com.example.demo.model.User;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Naeem <naeemark@gmail.com>.
 * <p>
 * Created on: 2020-04-11
 */
@Api(tags = "Retrieve Users")
@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "Get All Users", notes = "Gets list of all users", response = User.class, responseContainer = "List", tags = {"Retrieve Users"})
    @GetMapping(value = "/user/all")
    public List<User> findAll() {

        List<User> list = userRepository.findAll();
        logger.info(String.valueOf(list));
        return list;
    }

    @ApiOperation(value = "Get User by Email", response = User.class, tags = {"Retrieve Users"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Validation Error")
    })
    @GetMapping(value = "/user/{email}")
    public User findByEmail(@PathVariable String email) {

        if (!Utils.isValidEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Validation Error");
        }

        logger.info(String.valueOf(email));
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested data not found");
        return user;
    }
}
