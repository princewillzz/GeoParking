package xyz.willz.geoparking.controller.api_controller;

import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.willz.geoparking.dto.ParkingDTO;
import xyz.willz.geoparking.mapper.ParkingMapper;
import xyz.willz.geoparking.model.Parking;
import xyz.willz.geoparking.service.ParkingService;

@RestController
@RequestMapping(value = "/api")
public class ParkingRestController {

    private Logger logger = LoggerFactory.getLogger(ParkingRestController.class);

    private final ParkingService parkingService;

    private final ApplicationContext applicationContext;

    @Autowired
    protected ParkingRestController(
        @Qualifier("parkingService") final ParkingService parkingService,
        final ApplicationContext applicationContext
    ) {
        this.parkingService = parkingService;

        this.applicationContext = applicationContext;
    }

    @GetMapping("/parking")
    public ResponseEntity<?> getParkings() {
        
        return ResponseEntity.ok(parkingService.getAllparkings().parallelStream().map(it -> 
            applicationContext.getBean(ParkingMapper.class).toParkingDTO(it)
        ).collect(Collectors.toList()));
    }


    // Search parkings with address similiar to the key
    @GetMapping("/parking/search/")
    public ResponseEntity<?> getParkingsForAddress(
        @RequestBody(required = true) final ParkingDTO parkingDTO
    ) {

        return ResponseEntity.ok().body(parkingService.searchParkingsForAddress(parkingDTO.getAddress()));
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
            logger.error(e.getMessage());
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
                applicationContext.getBean(ParkingMapper.class).toParkingDTO(parking)
            );
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return ResponseEntity.badRequest().build();
    }

}
