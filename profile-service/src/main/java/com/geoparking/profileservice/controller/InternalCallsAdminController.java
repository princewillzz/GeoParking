package com.geoparking.profileservice.controller;

import com.geoparking.profileservice.entity.Profile;
import com.geoparking.profileservice.service.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/internal")
@Slf4j
public class InternalCallsAdminController {

    private final ProfileService profileService;

    @Autowired
    public InternalCallsAdminController(@Qualifier("profileService") final ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/admin/profile/{profile-id}")
    public ResponseEntity<Profile> getProfile(@PathVariable("profile-id") final String customerId) {

        return ResponseEntity.ok().body(profileService.loadProfileById(customerId));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/awake")
    public ResponseEntity<?> awakeMe() {
        log.info(" Woke me Up");
        return ResponseEntity.ok().build();
    }

}
