package com.appsecurity.securityapp.serevice;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import com.appsecurity.securityapp.dto.LocationRequest;
import com.appsecurity.securityapp.model.Location;
import com.appsecurity.securityapp.repository.LocationRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public Location updateLocation(String username, LocationRequest request){
        Location location = new Location();
        location.setUserId(username);   
        location.setLatitude(request.getLatitude());
        location.setLongitude(request.getLongitude());
        location.setTimestamp(Instant.now());

        return locationRepository.save(location);
    }

    public List<Location> getLocationHistory(String username) {
        return locationRepository.findByUserId(username);
    }

    public Location getLatestLocation(String username) {
        return locationRepository.findByUserId(username).stream()
                .max(Comparator.comparing(Location::getTimestamp))
                .orElse(null);
    }
}
