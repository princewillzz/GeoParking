package com.geoparking.parkingservice.service;

import java.util.List;
import java.util.stream.Collectors;

import com.geoparking.parkingservice.dto.ParkingDTO;
import com.geoparking.parkingservice.mapper.ParkingMapper;
import com.geoparking.parkingservice.model.Parking;
import com.geoparking.parkingservice.repository.ParkingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

//import com.geoparking.parkingservice.mapper.ParkingMapper;

@Service
@Qualifier("parkingService")
public class ParkingService {

    private final ParkingRepository parkingRepository;
    private final ParkingMapper parkingMapper;

    @Autowired
    public ParkingService(final ParkingRepository parkingRepository, final ParkingMapper parkingMapper) {
        this.parkingRepository = parkingRepository;
        this.parkingMapper = parkingMapper;
    }

    // Get all parkings list
    public List<ParkingDTO> getAllParkings() {
        return parkingRepository.findAll().stream().map(parking -> parkingMapper.toDTO(parking))
                .collect(Collectors.toList());
    }

    // Add a parking
    public ParkingDTO createParking(final ParkingDTO parkingDTO) {

        final Parking parking = parkingMapper.toEntity(parkingDTO);

        return parkingMapper.toDTO(parkingRepository.save(parking));
    }

}
