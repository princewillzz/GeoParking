package com.geoparking.gatewayserver.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class AwakeAllServices {

    private final RestTemplate restTemplate;

    final Logger log = LoggerFactory.getLogger(AwakeAllServices.class);

    @Autowired
    public AwakeAllServices(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/api/awake")
    public ResponseEntity<?> make(final HttpServletRequest request) {

        log.info(request.getRemoteAddr() + " woke me Up");
        try {
            Object a = null;

            System.err.println("Calll 0");
            // a = restTemplate.getForEntity("http://parking-service/parking/featured",
            // Object.class);
            System.err.println(a);
            a = restTemplate.getForObject("http://parking-service/internal/awake", Object.class);
            System.out.println(a);

            // a = restTemplate.getForObject("http://profile-service/internal/awake",
            // Object.class);
            // a = restTemplate.getForObject("http://booking-service/internal/awake",
            // Object.class);

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }

        return ResponseEntity.ok().build();
    }

}
