package com.geoparking.gatewayserver.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AwakeAllServices {

    private final RestTemplate restTemplate;

    final Logger log = LoggerFactory.getLogger(AwakeAllServices.class);

    @Autowired
    public AwakeAllServices(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/api/awake")
    public ResponseEntity<?> awakeme(final HttpServletRequest request) {

        log.info(request.getRemoteAddr() + " woke me Up");

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/api/awake/all")
    public ResponseEntity<?> awakeAll(final HttpServletRequest request) {

        log.info(request.getRemoteAddr() + " woke me Up");
        try {

            restTemplate.getForObject("http://parking-service/internal/awake", Object.class);

            restTemplate.getForObject("http://profile-service/internal/awake", Object.class);

            restTemplate.getForObject("http://booking-service/internal/awake", Object.class);

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }

        return ResponseEntity.ok().build();
    }
}
