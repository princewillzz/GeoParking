package com.geoparking.bookingservice.controller;

import javax.annotation.PostConstruct;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/internal")
public class InternalController {

    WebClient.Builder webcilentBuilder;

    @PostConstruct
    void init() {
        this.webcilentBuilder = WebClient.builder();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/awake")
    public ResponseEntity<?> awakeMe() {

        webcilentBuilder.build().get().uri("http://localhost:8080/api/awake").retrieve().bodyToMono(Object.class)
                .onErrorResume((e) -> Mono.just(new Object())).subscribe();

        return ResponseEntity.ok().build();
    }

}
