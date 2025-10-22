package com.appsecurity.securityapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsecurity.securityapp.dto.LocationRequest;
import com.appsecurity.securityapp.model.Location;
import com.appsecurity.securityapp.serevice.LocationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    
    @PostMapping("/update")
    public ResponseEntity<Location> updateLocation(@RequestBody LocationRequest request,
                                                   Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(locationService.updateLocation(username, request));
    }

    @GetMapping("/history")
    public ResponseEntity<List<Location>> getUserLocationHistory(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(locationService.getLocationHistory(username));
    }

    @GetMapping("/latest")
    public ResponseEntity<Location> getUserLatestLocation(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(locationService.getLatestLocation(username));
    }
}
