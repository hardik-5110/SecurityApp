package com.appsecurity.securityapp.model;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String role;

    private List<TrustedContact> trustedContact;

    private String createdAt;
    private String updatedAt;

    private boolean active = true;
}
