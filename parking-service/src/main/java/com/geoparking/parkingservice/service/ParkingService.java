package com.geoparking.parkingservice.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.geoparking.parkingservice.Exception.InvalidParkingAddressException;
import com.geoparking.parkingservice.dto.ParkingDTO;
import com.geoparking.parkingservice.mapper.ParkingMapper;
import com.geoparking.parkingservice.model.Parking;
import com.geoparking.parkingservice.repository.ParkingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

//import com.geoparking.parkingservice.mapper.ParkingMapper;

@Service
@Qualifier("parkingService")
@Slf4j
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
        return parkingRepository.findAll().stream().map(parkingMapper::toDTO).collect(Collectors.toList());
    }

    // Get a parking
    public ParkingDTO getParking(final String parkingId) throws NoSuchElementException {
        return parkingMapper.toDTO(parkingRepository.findById(parkingId).orElseThrow());
    }

    // Add a parking
    public ParkingDTO createParking(final ParkingDTO parkingDTO) {

        log.info("Saving a new parking info in the database");

        final Parking parking = parkingMapper.toEntity(parkingDTO);

        return parkingMapper.toDTO(parkingRepository.save(parking));
    }

    // Search List of Parkings based on the address provided
    public Set<ParkingDTO> fetchParkingsWithAddress(final String address) {

        // address must not be blanck and must be of length greater than 3
        if (address.isBlank()) { // || address.length() < 3, put this check later
            throw new InvalidParkingAddressException("Address insufficient...!");
        }

        final Pageable pageable = PageRequest.of(0, 20);

        return parkingRepository.findBySimiliarAddress(address, pageable).stream().map(parkingMapper::toDTO)
                .collect(Collectors.toSet());

    }

}
