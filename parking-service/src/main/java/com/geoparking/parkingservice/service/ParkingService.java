package com.geoparking.parkingservice.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;

import com.geoparking.parkingservice.Exception.InvalidParkingAddressException;
import com.geoparking.parkingservice.dto.ParkingDTO;
import com.geoparking.parkingservice.mapper.ParkingMapper;
import com.geoparking.parkingservice.model.Parking;
import com.geoparking.parkingservice.repository.ParkingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

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

    // Get all parking from the database then covert it to dto
    public List<ParkingDTO> getAllParkings() {
        return fetchAllparkingFromDatabase().stream().map(parkingMapper::toDTO).collect(Collectors.toList());
    }

    // get all parking from the database
    @Transactional(readOnly = true)
    private List<Parking> fetchAllparkingFromDatabase() {
        return parkingRepository.findAll();
    }

    /**
     * retrive parking with activity status
     * 
     * @param status active status
     * @return list of parking dto
     */
    public List<ParkingDTO> getAllParkingsWithStatus(final boolean status) {
        return fetchAllWithStatusFromDatabase(status).parallelStream().map(parkingMapper::toDTO)
                .collect(Collectors.toList());
    }

    // database query
    @Transactional(readOnly = true)
    private List<Parking> fetchAllWithStatusFromDatabase(final boolean status) {
        return parkingRepository.findByActive(status);
    }

    // Get a parking
    public ParkingDTO getParking(final String parkingId) throws NoSuchElementException {
        return parkingMapper.toDTO(fetchPakringWithIdFromDatabase(parkingId));
    }

    // get the parking Entity from the database
    @Transactional(readOnly = true)
    private Parking fetchPakringWithIdFromDatabase(final String parkingId) throws NoSuchElementException {
        return parkingRepository.findById(parkingId).orElseThrow();
    }

    // Add a parking to the database then return its dto
    public ParkingDTO createParking(final ParkingDTO parkingDTO) throws ConstraintViolationException {

        log.info("Saving a new parking info in the database");

        final Parking parking = parkingMapper.toEntity(parkingDTO);

        return parkingMapper.toDTO(saveParkingToDatabase(parking));
    }

    @Transactional
    private Parking saveParkingToDatabase(final Parking parking) {
        validateParkingEntity(parking);
        return parkingRepository.save(parking);
    }

    // Search List of Parkings based on the address provided then convert into dto
    public Set<ParkingDTO> fetchParkingsWithAddress(final String address) {

        // address must not be blanck and must be of length greater than 3
        if (address.isBlank()) { // || address.length() < 3, put this check later
            throw new InvalidParkingAddressException("Address insufficient...!");
        }

        final Pageable pageable = PageRequest.of(0, 20);

        return fetchParkingsSimiliarToAddressFromDatabase(address, pageable).stream().map(parkingMapper::toDTO)
                .collect(Collectors.toSet());

    }

    // Fetch similiar address parking data from the database
    private List<Parking> fetchParkingsSimiliarToAddressFromDatabase(final String address, final Pageable pageable) {
        return parkingRepository.findBySimiliarAddress(address, pageable);
    }

    @Transactional
    public ParkingDTO updateParkingInfo(ParkingDTO parkingDTO)
            throws ConstraintViolationException, IllegalStateException {

        if (parkingDTO.getId() == null)
            throw new IllegalStateException("Insufficient data...");

        final Parking parkingToUpdate = fetchPakringWithIdFromDatabase(parkingDTO.getId());

        setFieldsToUpdate(parkingToUpdate, parkingDTO);

        // Generic validation
        validateParkingEntity(parkingToUpdate);

        return parkingMapper.toDTO(saveParkingToDatabase(parkingToUpdate));

    }

    // Change the field that can be updated
    private void setFieldsToUpdate(final Parking parking, final ParkingDTO parkingDTO) {

        parking.setAddress(parkingDTO.getAddress());
        parking.setHourlyRent(parkingDTO.getHourlyRent());
        parking.setName(parkingDTO.getName());

    }

    // Validate parking entity absed on the constaint provided in the parking entity
    private void validateParkingEntity(final Parking parking) throws ConstraintViolationException {
        Set<ConstraintViolation<Parking>> violations = Validation.buildDefaultValidatorFactory().getValidator()
                .validate(parking);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    // Delete a parking
    @Transactional
    public void deleteParkingWithId(final String parkingId) {

        final Parking parking = fetchPakringWithIdFromDatabase(parkingId);
        parking.setActive(false);

        saveParkingToDatabase(parking);

    }

    /**
     * @return a list of parking DTO containing featured parkings currently the most
     *         booked parkings
     */
    public Set<ParkingDTO> getFeaturedParkings() {
        return getMostPopularParkingsFromDatabase().stream().map(parkingMapper::toDTO).collect(Collectors.toSet());
    }

    private Page<Parking> getMostPopularParkingsFromDatabase() {

        return parkingRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "timeBooked")));
    }

}
