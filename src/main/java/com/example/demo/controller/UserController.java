package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Naeem <naeemark@gmail.com>.
 * <p>
 * Created on: 2020-04-11
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @GetMapping(value = "/user")
    public List<User> findAll() {
        return userJpaRepository.findAll();
    }

    @GetMapping(value = "/userByEmail")
    public User findByEmail(@PathVariable String email) {
        return userJpaRepository.findByEmail(email);
    }

    @PostMapping(value = "/user")
    public User saveUser(@RequestBody User user) {
        return userJpaRepository.save(user);
    }
}
