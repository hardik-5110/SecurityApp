package com.appsecurity.securityapp.serevice;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.appsecurity.securityapp.model.Location;
import com.appsecurity.securityapp.model.SosLog;
import com.appsecurity.securityapp.model.User;
import com.appsecurity.securityapp.repository.LocationRepository;
import com.appsecurity.securityapp.repository.SosLogRepository;
import com.appsecurity.securityapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final SosLogRepository sosLogRepository;

    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Location getUserLatestLocation(String username) {
        List<Location> locations = locationRepository.findByUserId(username);

        if (locations.isEmpty()) {
            throw new RuntimeException("No locations found for user: " + username);
        }

        return locations.stream()
                .max(Comparator.comparing(Location::getTimestamp))
                .orElseThrow(() -> new RuntimeException("Could not determine latest location"));
    }

    public List<SosLog> getAllSosLogs() {
        return sosLogRepository.findAll();
    }

    public List<SosLog> getSosLogsByUser(String username) {
        return sosLogRepository.findByUsername(username);
    }

    public SosLog getLatestSosLogByUser(String username) {
        return sosLogRepository.findByUsername(username).stream()
                .max(Comparator.comparing(SosLog::getTimestamp))
                .orElse(null);
    }

    public List<SosLog> getLatestSosLogsForAllUsers() {
        List<SosLog> allLogs = sosLogRepository.findAll();

        return allLogs.stream()
                .collect(Collectors.groupingBy(SosLog::getUsername))
                .values().stream()
                .map(logs -> logs.stream()
                        .max(Comparator.comparing(SosLog::getTimestamp))
                        .orElse(null))
                .toList();
    }

    public SosLog resolveSos(String sosId) {
        SosLog log = sosLogRepository.findById(sosId)
            .orElseThrow(() -> new RuntimeException("SOS not found"));
        log.setResolved(true);
        return sosLogRepository.save(log);
    }

    public User deactivateUser(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(false);
        return userRepository.save(user);
    }

    public User activateUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(true);
        return userRepository.save(user);
    }
}
