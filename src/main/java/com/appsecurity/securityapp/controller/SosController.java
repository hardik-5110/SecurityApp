package com.appsecurity.securityapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsecurity.securityapp.model.SosLog;
import com.appsecurity.securityapp.serevice.SosService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/sos")
@RequiredArgsConstructor
public class SosController {
    private final SosService sosService;

    @PostMapping("/trigger")
    public ResponseEntity<String> triggerSos(Authentication authentication) {
        String username = authentication.getName();
        String result = sosService.triggerSos(username);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/logs")
    public ResponseEntity<List<SosLog>> getUserSosLogs(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(sosService.getUserSosLogs(username));
    }
    
}
