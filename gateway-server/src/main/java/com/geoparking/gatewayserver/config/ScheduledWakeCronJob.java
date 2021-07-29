package com.geoparking.gatewayserver.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ScheduledWakeCronJob {

    @Autowired
    RestTemplate restTemplate;

    Logger log = LoggerFactory.getLogger(ScheduledWakeCronJob.class);

    WebClient.Builder webClientBuilder;

    @PostConstruct
    void init() {
        this.webClientBuilder = WebClient.builder();
    }

    @Scheduled(initialDelayString = "PT5M", fixedDelayString = "PT5M")
    void awakeOtherServices() {

        try {

            // parking service

            webClientBuilder.build().get().uri("https://geoparking-parking.herokuapp.com/internal/awake").retrieve()
                    .bodyToMono(Object.class).subscribe();

            // profile service
            webClientBuilder.build().get().uri("https://geoparking-profile.herokuapp.com/internal/awake").retrieve()
                    .bodyToMono(Object.class).subscribe();

            // booking service
            webClientBuilder.build().get().uri("https://geoparking-booking.herokuapp.com/internal/awake").retrieve()
                    .bodyToMono(Object.class).subscribe();

            // profile service
            // restTemplate.getForObject("http://parking-service/internal/awake",
            // Object.class);
            // System.err.println("got park");

            // restTemplate.getForObject("http://profile-service/internal/awake",
            // Object.class);
            // System.err.println("got pro");

            // restTemplate.getForObject("http://booking-service/internal/awake",
            // Object.class);
            // System.err.println("got book");

        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

}
