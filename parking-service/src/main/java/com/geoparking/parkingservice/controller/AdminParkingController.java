package com.geoparking.parkingservice.controller;

import javax.servlet.http.HttpServletRequest;

import com.geoparking.parkingservice.dto.ParkingDTO;
import com.geoparking.parkingservice.model.DecodedUserInfo;
import com.geoparking.parkingservice.service.ParkingService;
import com.geoparking.parkingservice.utility.WithUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminParkingController {

    private final ParkingService parkingService;

    @Autowired
    public AdminParkingController(final ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping
    public String hello(HttpServletRequest request) {
        // System.err.println(principal);
        System.err.println(request.getHeader("Authorization"));
        return "Hello parking addmin";
    }

    @GetMapping("/my-parkings")
    public ResponseEntity<?> getMyParkings(@RequestParam(name = "active", required = false) final Boolean parkingStatus,
            @WithUser DecodedUserInfo adminInfo) {

        if (parkingStatus != null) {
            return ResponseEntity.ok().body(parkingService.getListOfMyParkingsWithStatus(adminInfo, parkingStatus));
        }

        return ResponseEntity.ok().body(parkingService.getListOfMyParkings(adminInfo));
    }

    @PostMapping("/parking")
    public ResponseEntity<?> createParking(@RequestBody final ParkingDTO parkingDTO,
            @WithUser final DecodedUserInfo adminInfo) {

        return ResponseEntity.ok(parkingService.createParking(parkingDTO, adminInfo));
    }

    @PutMapping("/parking")
    public ResponseEntity<?> handleUpdateParking(@RequestBody final ParkingDTO parkingDTO,
            @WithUser final DecodedUserInfo adminInfo) {

        return ResponseEntity.ok().body(parkingService.updateParkingInfo(parkingDTO, adminInfo));

    }

    @DeleteMapping("/parking/{id}")
    public ResponseEntity<?> handleDeleteParkingWithId(@PathVariable("id") final String parkingId,
            @WithUser final DecodedUserInfo adminInfo) {

        parkingService.deleteParkingWithId(parkingId, adminInfo);

        return ResponseEntity.ok().build();
    }

}
