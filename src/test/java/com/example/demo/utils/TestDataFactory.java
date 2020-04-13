package com.example.demo.utils;

import com.example.demo.model.Feature;
import com.example.demo.model.User;

import java.util.HashSet;

public class TestDataFactory {

    public static User getSingleUser(Integer id, String email){
        return new User(id, email, new HashSet<>());
    }

    public static Feature getSingleFeature(Integer id, String name){
        return new Feature(id, name);
    }


}
