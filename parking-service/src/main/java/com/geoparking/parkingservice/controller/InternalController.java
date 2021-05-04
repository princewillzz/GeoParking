package com.geoparking.parkingservice.controller;

import com.geoparking.parkingservice.dto.ParkingDTO;
import com.geoparking.parkingservice.model.DecodedUserInfo;
import com.geoparking.parkingservice.service.ParkingService;
import com.geoparking.parkingservice.utility.WithUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
public class InternalController {

    @Autowired
    private ParkingService parkingService;

    @GetMapping(value = "/admin/parking/{id}/increment/booked")
    public ResponseEntity<ParkingDTO> incrementTimesBooked(@PathVariable("id") String parkingId,
            @WithUser final DecodedUserInfo userInfo) {

        final ParkingDTO parking = parkingService.incrementTimesBooked(parkingId);

        return ResponseEntity.ok(parking);

    }

}
