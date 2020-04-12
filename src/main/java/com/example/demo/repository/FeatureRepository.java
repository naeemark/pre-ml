package com.example.demo.repository;

import com.example.demo.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * Created by Naeem <naeemark@gmail.com>.
 * <p>
 * Created on: 2020-04-12
 */

@Component
public interface FeatureRepository extends JpaRepository<Feature, Integer> {

    Feature findByName(String featureName);
}
