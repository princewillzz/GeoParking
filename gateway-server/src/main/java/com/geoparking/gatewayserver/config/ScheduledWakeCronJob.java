package com.geoparking.gatewayserver.config;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ScheduledWakeCronJob {

    WebClient webClient = WebClient.create();
    @Autowired
    RestTemplate restTemplate;

    Logger log = LoggerFactory.getLogger(ScheduledWakeCronJob.class);

    // @Scheduled(initialDelayString = "PT10S", fixedDelayString = "PT30S")
    void awakeOtherServices() {

        // Parking service

        try {

            log.info("Waking all at " + new Date().toString());
            restTemplate.getForObject("http://parking-service/internal/awake", Object.class);

            restTemplate.getForObject("http://profile-service/internal/awake", Object.class);

            restTemplate.getForObject("http://booking-service/internal/awake", Object.class);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

}
