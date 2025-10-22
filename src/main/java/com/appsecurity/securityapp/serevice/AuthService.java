package com.appsecurity.securityapp.serevice;

import java.time.Instant;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.appsecurity.securityapp.dto.AuthResponse;
import com.appsecurity.securityapp.dto.LoginRequest;
import com.appsecurity.securityapp.dto.RegisterRequest;
import com.appsecurity.securityapp.model.User;
import com.appsecurity.securityapp.repository.UserRepository;
import com.appsecurity.securityapp.security.JwtUtil;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(Instant.now().toString());
        user.setUpdatedAt(Instant.now().toString());

        userRepository.save(user);
        AuthResponse response = new AuthResponse();
        response.setMessage("User Registration is Successfull");
        return response;
    }

    public AuthResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        AuthResponse response = new AuthResponse();
        response.setMessage("Login Successfull");
        response.setToken(token);
        return response;
    }
}
