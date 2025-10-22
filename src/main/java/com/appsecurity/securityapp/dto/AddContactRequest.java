package com.appsecurity.securityapp.dto;

import lombok.Data;

@Data
public class AddContactRequest {
    private String name;
    private String email;
    private String phone;
}
