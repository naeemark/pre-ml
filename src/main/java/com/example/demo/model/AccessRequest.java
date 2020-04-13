package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Created by Naeem <naeemark@gmail.com>.
 * <p>
 * Created on: 2020-04-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccessRequest {

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Must be a valid email address")
    private String email;

    @NotBlank(message = "Feature Name is mandatory")
    private String featureName;

    private boolean enable;
}
