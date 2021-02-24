package xyz.willz.geoparking.controller.api_controller;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import xyz.willz.geoparking.dto.ParkingDTO;
import xyz.willz.geoparking.mapper.ParkingMapper;
import xyz.willz.geoparking.model.Parking;
import xyz.willz.geoparking.service.ParkingService;
import xyz.willz.geoparking.utilities.ParkingAvailabilityForm;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class ParkingRestController {

    private final ParkingService parkingService;


    @Autowired
    protected ParkingRestController(
        @Qualifier("parkingService") final ParkingService parkingService
    ) {
        this.parkingService = parkingService;

    }

    @GetMapping("/parking/is-available")
    public ResponseEntity<HttpStatus> checkAvailabilityForParking(@RequestBody final ParkingAvailabilityForm details) {

        try {
            System.out.println(details);

            final boolean isAvailable = parkingService.isParkingAvailable(details);
            if(isAvailable) {
                return ResponseEntity.ok().build();
            }
            
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }


    // Search parkings with address similar to the key
    @PostMapping("/parking/search")
    public ResponseEntity<?> getParkingsForAddress(
        @RequestBody(required = true) final String parkingAddress
    ) {
        return ResponseEntity.ok().body(parkingService.searchParkingsForAddress(parkingAddress));
    }


    @GetMapping("/parking")
    public ResponseEntity<?> getParkings() {
        
        return ResponseEntity.ok(parkingService.getAllparkings().parallelStream().map(parking -> 
            ParkingMapper.INSTANCE.toParkingDTO(parking)
        ).collect(Collectors.toList()));
    }

    


    // Consume json to save a new parking
    @PostMapping("/parking")
    public ResponseEntity<?> createParking(
        @RequestBody final ParkingDTO parkingDTO
    ) {

        try {
            // send parking dto to the service layer for saving it in the databases
            return ResponseEntity.ok(parkingService.saveParking(parkingDTO));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        
        return ResponseEntity.badRequest().build();
    }



    // Get parking details for a particular id
    @GetMapping("/parking/{id}")
    public ResponseEntity<?> getParkingWithId(
        @PathVariable("id") final UUID id
    ) {
        
        try {
            // Fetch Parking entity from the parking service
            final Parking parking = parkingService.getParking(id);

            // Get mapper using context instance and map the entity to DTO then return 
            return ResponseEntity.ok().body(
                ParkingMapper.INSTANCE.toParkingDTO(parking)
            );
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return ResponseEntity.badRequest().build();
    }


    // Get some featured parkings
    @GetMapping(value = "/parking/featured")
    public ResponseEntity<?> getPopularParkings() {

        return ResponseEntity.ok().body(parkingService.getPopularParkings());
    }

}
