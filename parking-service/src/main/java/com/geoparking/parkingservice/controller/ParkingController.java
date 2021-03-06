package com.geoparking.parkingservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.geoparking.parkingservice.dto.ParkingCoordinate;
import com.geoparking.parkingservice.dto.ParkingDTO;
import com.geoparking.parkingservice.service.ParkingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ParkingController {

    private ParkingService parkingService;

    @Autowired
    public ParkingController(@Qualifier("parkingService") final ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/parkings")
    public ResponseEntity<?> getParkings(@RequestParam(name = "active", required = false) final Boolean status) {

        List<ParkingDTO> parkingsList;

        // If status is present then filter according else get all the parkings
        if (status != null) {
            parkingsList = parkingService.getAllParkingsWithStatus(status);
        } else {
            parkingsList = parkingService.getAllParkings();
        }

        return ResponseEntity.ok(parkingsList);
    }

    @GetMapping(value = "/parking/search")
    public ResponseEntity<?> searchParkingWithAddress(@RequestParam("address") final String address) {
        return ResponseEntity.ok(parkingService.fetchParkingsWithAddress(address));
    }

    @GetMapping(value = "/parking/{id}")
    public ResponseEntity<?> getparkingWithId(@PathVariable("id") final String parkingId) throws InterruptedException {
        return ResponseEntity.ok(parkingService.getParking(parkingId));
    }

    @GetMapping(value = "/parking/featured")
    public ResponseEntity<?> getFeaturedParkingList(HttpServletRequest request) {

        // parkingService.getFeaturedParkings().stream().forEach(p ->
        // System.err.println(p.getId()));

        log.info("Fetching featured parking info");

        return ResponseEntity.ok().body(parkingService.getFeaturedParkings());
    }

    @GetMapping(value = "/parking/nearby")
    public ResponseEntity<?> getNearbyParking(@RequestParam("lat") double latitude,
            @RequestParam("lng") double longitude, @RequestParam double distance) {

        ParkingCoordinate coords = new ParkingCoordinate(latitude, longitude);

        System.err.println(distance);
        return ResponseEntity.ok(parkingService.searchNearlyParkingDTOs(coords, distance));
    }

}
