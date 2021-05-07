package com.geoparking.profileservice.config;

import java.util.Arrays;
import java.util.List;

import com.geoparking.profileservice.dto.ProfileDTO;
import com.geoparking.profileservice.service.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class TestProfileRunner implements CommandLineRunner {

    @Autowired
    private ProfileService profileService;

    @Override
    public void run(String... args) throws Exception {

        List<ProfileDTO> profiles = Arrays.asList(
                ProfileDTO.builder().email("harsh").password("harsh").role("user").firstName("firstname")
                        .mobile("8918930270").isActive(true).build(),
                ProfileDTO.builder().email("testadmin").password("testadmin").role("admin").firstName("firstName")
                        .mobile("9832229165").isActive(true).build());

        for (ProfileDTO profile : profiles) {
            try {
                profileService.loadUserByUsername(profile.getEmail());
            } catch (UsernameNotFoundException e) {
                profileService.createNewProfile(profile);
            }
        }

    }

}
