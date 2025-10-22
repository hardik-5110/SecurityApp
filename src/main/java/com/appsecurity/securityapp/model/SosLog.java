package com.appsecurity.securityapp.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "sos_logs")
public class SosLog {
    @Id
    private String id;
    private String username;
    private String message;
    private Instant Timestamp;
    private Double latitude;   
    private Double longitude; 
    private boolean resolved = false;
}
