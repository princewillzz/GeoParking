package com.geoparking.parkingservice.controller;

import javax.servlet.http.HttpServletRequest;

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

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/internal")
@Slf4j
public class InternalController {

    @Autowired
    private ParkingService parkingService;

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
        return ResponseEntity.ok().build();
    }

}
