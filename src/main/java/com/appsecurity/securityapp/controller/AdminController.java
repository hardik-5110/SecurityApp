package com.appsecurity.securityapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsecurity.securityapp.model.Location;
import com.appsecurity.securityapp.model.SosLog;
import com.appsecurity.securityapp.model.User;
import com.appsecurity.securityapp.serevice.AdminService;

import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }
    
    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        return ResponseEntity.ok(adminService.getUserByUsername(username));
    }
    
    @GetMapping("/user/{username}/locations")
    public ResponseEntity<Location> getUserLatestLocation(@PathVariable String username){
        return ResponseEntity.ok(adminService.getUserLatestLocation(username));
    }

    @GetMapping("/sos-logs")
    public ResponseEntity<List<SosLog>> getAllSosLogs() {
        return ResponseEntity.ok(adminService.getAllSosLogs());
    }

    @GetMapping("/sos-logs/{username}")
    public ResponseEntity<List<SosLog>> getSosLogsByUser(@PathVariable String username) {
        return ResponseEntity.ok(adminService.getSosLogsByUser(username));
    }
    
    @GetMapping("/sos-logs/{username}/latest")
    public ResponseEntity<SosLog> getLatestSosLogsByUser(@PathVariable String username) {
        SosLog latest = adminService.getLatestSosLogByUser(username);
        if (latest == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(latest);
    }

    @GetMapping("/sos-logs/latest")
    public ResponseEntity<List<SosLog>> getLatestSosLogsForAllUsers() {
        return ResponseEntity.ok(adminService.getLatestSosLogsForAllUsers());
    }
    
    @PutMapping("/sos-logs/{id}/resolve")
    public ResponseEntity<SosLog> resolveSos(@PathVariable String id) {
        return ResponseEntity.ok(adminService.resolveSos(id));
    }

    @PutMapping("/user/{username}/deactivate")
    public ResponseEntity<User> deactivateUser(@PathVariable String username) {
        return ResponseEntity.ok(adminService.deactivateUser(username));
    }

    @PutMapping("/user/{username}/activate")
    public ResponseEntity<User> activateUser(@PathVariable String username) {
        return ResponseEntity.ok(adminService.activateUser(username));
    }

}
