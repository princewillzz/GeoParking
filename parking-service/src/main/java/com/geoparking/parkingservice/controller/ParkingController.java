package com.geoparking.parkingservice.controller;

import com.geoparking.parkingservice.dto.ParkingDTO;
import com.geoparking.parkingservice.service.ParkingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingController {

    private ParkingService parkingService;

    @Autowired
    public ParkingController(final ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/parkings")
    public ResponseEntity<?> getParkings() {
        return ResponseEntity.ok(parkingService.getAllParkings());
    }

    @GetMapping(value = "/search/parking")
    public ResponseEntity<?> searchParkingWithAddress(@RequestParam("address") final String address) {

        return ResponseEntity.ok(parkingService.fetchParkingsWithAddress(address));

    }

    @GetMapping(value = "/parking")
    public ResponseEntity<?> getparkingWithId(@RequestParam("id") final String parkingId) {
        return ResponseEntity.ok(parkingService.getParking(parkingId));
    }

    @PostMapping("/parking")
    public ResponseEntity<?> createParking(@RequestBody final ParkingDTO parkingDTO) {

        return ResponseEntity.ok(parkingService.createParking(parkingDTO));
    }

}
