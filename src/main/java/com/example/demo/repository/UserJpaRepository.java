package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * Created by Naeem <naeemark@gmail.com>.
 * <p>
 * Created on: 2020-04-11
 */
@Component
public interface UserJpaRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    boolean existsByEmail(String email);
}
