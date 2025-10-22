package com.appsecurity.securityapp.serevice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.appsecurity.securityapp.dto.AddContactRequest;
import com.appsecurity.securityapp.model.TrustedContact;
import com.appsecurity.securityapp.model.User;
import com.appsecurity.securityapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrustedContactService {
    private final UserRepository userRepository;

    public void addTrustedContacts(String username,AddContactRequest request){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TrustedContact trustedContact = new TrustedContact();
        trustedContact.setName(request.getName());
        trustedContact.setPhone(request.getPhone());
        trustedContact.setEmail(request.getEmail());

        if (user.getTrustedContact()==null) {
            user.setTrustedContact(new ArrayList<>());
        }

        user.getTrustedContact().add(trustedContact);
        user.setUpdatedAt(Instant.now().toString());

        userRepository.save(user);
    }

    public List<TrustedContact> getTrustedContacts(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getTrustedContact();
    }

    public void deleteTrustedContact(String username, String phone) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getTrustedContact() != null) {
            user.getTrustedContact().removeIf(c -> c.getPhone().equals(phone));
        }

        user.setUpdatedAt(Instant.now().toString());
        userRepository.save(user);
    }
}
