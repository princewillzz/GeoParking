package xyz.willz.geoparking.controller.api_controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import xyz.willz.geoparking.service.ParkingService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/renter")
@AllArgsConstructor
public class RenterControllerAPI {

    private ParkingService parkingService;

    @GetMapping(value = "/parking")
    public ResponseEntity<?> getAllParkings() {

        return ResponseEntity.ok(parkingService.getAllparkings());
    }

}


