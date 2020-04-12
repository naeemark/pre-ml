package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    private String email;
    private String featureName;
    private Boolean enable;
}
