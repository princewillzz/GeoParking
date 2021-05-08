package com.geoparking.parkingservice.controller;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import com.geoparking.parkingservice.dto.ParkingDTO;
import com.geoparking.parkingservice.model.DecodedUserInfo;
import com.geoparking.parkingservice.service.ParkingService;
import com.geoparking.parkingservice.utility.WithUser;

import org.springframework.beans.factory.annotation.Autowired;
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
public class InternalController {

    @Autowired
    private ParkingService parkingService;

    WebClient.Builder webclientBuilder;

    @PostConstruct
    void init() {
        this.webclientBuilder = WebClient.builder();
    }

    @GetMapping(value = "/admin/parking/{id}/increment/booked")
    public ResponseEntity<ParkingDTO> incrementTimesBooked(@PathVariable("id") String parkingId,
            @WithUser final DecodedUserInfo userInfo) {

        final ParkingDTO parking = parkingService.incrementTimesBooked(parkingId);

        return ResponseEntity.ok(parking);

    }

    @CrossOrigin(origins = "*")
    @GetMapping("/awake")
    public ResponseEntity<?> awakeMe() {
        log.info(" Woke me Up");

        webclientBuilder.build().get().uri("https://geoparking-gateway.herokuapp.com/api/awake").retrieve()
                .bodyToMono(Object.class).onErrorResume((e) -> Mono.just(new Object())).subscribe(res -> {
                    log.info("Woke up API gateway");
                });

        return ResponseEntity.ok().build();
    }

}
