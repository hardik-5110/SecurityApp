package com.appsecurity.securityapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.appsecurity.securityapp.model.SosLog;

public interface SosLogRepository extends MongoRepository<SosLog,String>{  
    List<SosLog> findByUsername(String username);
} 
