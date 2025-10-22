package com.appsecurity.securityapp.repository;
import com.appsecurity.securityapp.model.User;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
