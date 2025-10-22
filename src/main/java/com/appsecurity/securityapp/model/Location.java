package com.appsecurity.securityapp.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "locations")
public class Location {
    @Id
    private String id;
    private String userId;
    private double latitude;
    private double longitude;
    private Instant timestamp;

}
