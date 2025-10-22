package com.appsecurity.securityapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsecurity.securityapp.dto.AddContactRequest;
import com.appsecurity.securityapp.dto.LocationRequest;
import com.appsecurity.securityapp.model.Location;
import com.appsecurity.securityapp.serevice.LocationService;
import com.appsecurity.securityapp.serevice.TrustedContactService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final LocationService locationService;
    private final TrustedContactService trustedContactService;

    @PostMapping("/location")
    public ResponseEntity<Location> updateLocation(@RequestBody LocationRequest request, Authentication authentication) {
        return ResponseEntity.ok(locationService.updateLocation(authentication.getName(), request));
    }

    @PostMapping("/contacts")
    public ResponseEntity<String> addTrustedContacts(@RequestBody AddContactRequest request, Authentication authentication) {        
        trustedContactService.addTrustedContacts(authentication.getName(), request);
        return ResponseEntity.ok("Contacts Added Successfully");
    }
    
    @GetMapping("/contacts")
    public ResponseEntity<?> getTrustedContacts(Authentication authentication) {
        return ResponseEntity.ok(trustedContactService.getTrustedContacts(authentication.getName()));
    }

    @DeleteMapping("/contacts/{phone}")
    public ResponseEntity<String> deleteTrustedContact(@PathVariable String phone,
                                                       Authentication authentication) {
        trustedContactService.deleteTrustedContact(authentication.getName(), phone);
        return ResponseEntity.ok("Contact deleted successfully");
    }
}
