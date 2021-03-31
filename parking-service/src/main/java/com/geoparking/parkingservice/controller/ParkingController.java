package com.geoparking.parkingservice.controller;

import com.geoparking.parkingservice.dto.ParkingDTO;
import com.geoparking.parkingservice.mapper.ParkingMapper;
import com.geoparking.parkingservice.model.Parking;
import com.geoparking.parkingservice.repository.ParkingRepository;
import com.geoparking.parkingservice.service.ParkingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingController {

    private ParkingService parkingService;

    @Autowired
    ParkingMapper mapper;

    @Autowired
    public ParkingController(final ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/get")
    public ResponseEntity<?> getParkings() {
        return ResponseEntity.ok(parkingService.getAllParkings());
    }

    @PostMapping("/post")
    public ResponseEntity<?> createParking(@RequestBody ParkingDTO parkingDTO) {

        return ResponseEntity.ok(parkingService.createParking(parkingDTO));
    }

}
