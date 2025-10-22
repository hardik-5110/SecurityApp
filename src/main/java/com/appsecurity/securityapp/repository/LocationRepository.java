package com.appsecurity.securityapp.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.appsecurity.securityapp.model.Location;
import java.util.List;


public interface LocationRepository extends MongoRepository<Location,String>{
    List<Location> findByUserId(String userId);
}
