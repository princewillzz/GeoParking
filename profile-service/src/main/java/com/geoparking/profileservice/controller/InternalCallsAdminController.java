package com.geoparking.profileservice.controller;

import javax.annotation.PostConstruct;

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
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/internal")
@Slf4j
public class InternalCallsAdminController {

    private final ProfileService profileService;

    private WebClient.Builder webClientBuilder;

    @PostConstruct
    void init() {
        this.webClientBuilder = WebClient.builder();
    }

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
        webClientBuilder.build().get().uri("https://geoparking-gateway.herokuapp.com/api/awake").retrieve()
                .bodyToMono(Object.class).onErrorResume(e -> Mono.just(new Object())).subscribe();

        return ResponseEntity.ok().build();
    }

}
