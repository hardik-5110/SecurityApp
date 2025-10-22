package com.appsecurity.securityapp.serevice;

import com.appsecurity.securityapp.model.Location;
import com.appsecurity.securityapp.model.SosLog;
import com.appsecurity.securityapp.model.TrustedContact;
import com.appsecurity.securityapp.model.User;
import com.appsecurity.securityapp.repository.SosLogRepository;
import com.appsecurity.securityapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SosService {

    private final UserRepository userRepository;
    private final SosLogRepository sosLogRepository;
    private final LocationService locationService;
    private final EmailService emailService;

    public String triggerSos(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isActive()) {
            throw new RuntimeException("User is deactivated and cannot trigger SOS");
        }
        
        List<TrustedContact> trustedContacts = user.getTrustedContact();
        if (trustedContacts == null || trustedContacts.isEmpty()) {
            return "No contacts saved in trusted contacts list";
        }

        Location latestLocation = locationService.getLatestLocation(username);

        String locationText = (latestLocation != null)
                ? "\nLast Known Location: https://www.google.com/maps?q="
                  + latestLocation.getLatitude() + "," + latestLocation.getLongitude()
                : "\nLocation not available.";

        String message = "🚨 SOS Alert!\n\nUser: " + user.getUsername()
                + " may be in danger. Please check on them immediately."
                + locationText;

        // Send alert to contacts
        trustedContacts.forEach(contact -> {
            if (contact.getEmail() != null) {
                emailService.sendEmail(contact.getEmail(), "🚨 SOS Alert", message);
            }
            // Future: SMS
            /*
            if (contact.getPhone() != null) {
                smsService.sendSms(contact.getPhone(), message);
            }
            */
        });

        SosLog log = new SosLog();
        log.setUsername(username);
        log.setMessage(message);
        log.setTimestamp(Instant.now());

        if (latestLocation != null) {
            log.setLatitude(latestLocation.getLatitude());
            log.setLongitude(latestLocation.getLongitude());
        }

        sosLogRepository.save(log);

        return "SOS Alert sent to " + trustedContacts.size() + " contacts via email!";
    }

    public List<SosLog> getUserSosLogs(String username) {
        return sosLogRepository.findByUsername(username);
    }
}
