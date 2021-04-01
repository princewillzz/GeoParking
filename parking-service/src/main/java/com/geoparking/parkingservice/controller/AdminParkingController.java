package com.geoparking.parkingservice.controller;

import com.geoparking.parkingservice.dto.ParkingDTO;
import com.geoparking.parkingservice.service.ParkingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminParkingController {

    private final ParkingService parkingService;

    @Autowired
    public AdminParkingController(final ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping("/parking")
    public ResponseEntity<?> createParking(@RequestBody final ParkingDTO parkingDTO) {

        return ResponseEntity.ok(parkingService.createParking(parkingDTO));
    }

    @PutMapping("/parking")
    public ResponseEntity<?> handleUpdateParking(@RequestBody final ParkingDTO parkingDTO) {

        return ResponseEntity.ok().body(parkingService.updateParkingInfo(parkingDTO));

    }

    @DeleteMapping("/parking/{id}")
    public ResponseEntity<?> handleDeleteParkingWithId(@PathVariable("id") final String parkingId) {
        parkingService.deleteParkingWithId(parkingId);
        return ResponseEntity.ok().build();
    }

}
