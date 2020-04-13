package com.naeemark.sa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by Naeem <naeemark@gmail.com>.
 * <p>
 * Created on: 2020-04-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccessResponse {

    private boolean canAccess;
}
