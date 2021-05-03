package com.geoparking.parkingservice.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;

import com.geoparking.parkingservice.Exception.InvalidParkingAddressException;
import com.geoparking.parkingservice.dto.ParkingCoordinate;
import com.geoparking.parkingservice.dto.ParkingDTO;
import com.geoparking.parkingservice.mapper.ParkingMapper;
import com.geoparking.parkingservice.model.DecodedUserInfo;
import com.geoparking.parkingservice.model.Parking;
import com.geoparking.parkingservice.repository.ParkingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Qualifier("parkingService")
@Slf4j
public class ParkingService {

    protected final ParkingRepository parkingRepository;
    protected final ParkingMapper parkingMapper;

    @Autowired
    public ParkingService(final ParkingRepository parkingRepository, final ParkingMapper parkingMapper) {
        this.parkingRepository = parkingRepository;
        this.parkingMapper = parkingMapper;
    }

    public List<ParkingDTO> searchNearlyParkingDTOs(ParkingCoordinate coords, double distance) {

        return searchNearbyParking(coords, distance).stream().map(parkingMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Parking> searchNearbyParking(ParkingCoordinate coords, double distance) {

        Point fromPoint = new Point(coords.getLatitude(), coords.getLongitude());
        Distance dist = new Distance(distance, Metrics.KILOMETERS);
        Circle area = new Circle(fromPoint, dist);

        log.info("Parking in area " + area);

        return parkingRepository.findByLocationWithin(area);

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
        return parkingMapper.toDTO(fetchParkingWithIdFromDatabase(parkingId));
    }

    // get the parking Entity from the database
    @Transactional(readOnly = true)
    private Parking fetchParkingWithIdFromDatabase(final String parkingId) throws NoSuchElementException {
        return parkingRepository.findById(parkingId).orElseThrow();
    }

    // Add a parking to the database then return its dto
    public ParkingDTO createParking(final ParkingDTO parkingDTO, final DecodedUserInfo adminInfo)
            throws ConstraintViolationException {

        log.info("Saving a new parking info in the database");

        final Parking parking = parkingMapper.toEntity(parkingDTO);
        parking.setActive(true);
        parking.setOwnerId(adminInfo.getUserId());

        GeoJsonPoint location = new GeoJsonPoint(parkingDTO.getPosition().getLatitude(),
                parkingDTO.getPosition().getLongitude());
        parking.setLocation(location);

        return parkingMapper.toDTO(saveParkingToDatabase(parking));
    }

    @Transactional
    private Parking saveParkingToDatabase(final Parking parking) {
        validateParkingEntity(parking);
        // return parking;
        return parkingRepository.save(parking);
    }

    // Search List of Parkings based on the address provided then convert into dto
    public Set<ParkingDTO> fetchParkingsWithAddress(final String address) {

        // address must not be blanck and must be of length greater than 3
        if (address.isBlank()) { // || address.length() < 3, put this check later
            throw new InvalidParkingAddressException("Address insufficient...!");
        }

        final Pageable pageable = PageRequest.of(0, 50);

        return fetchParkingsSimiliarToAddressFromDatabase(address, pageable).stream().map(parkingMapper::toDTO)
                .collect(Collectors.toSet());

    }

    // Fetch similiar address parking data from the database
    private List<Parking> fetchParkingsSimiliarToAddressFromDatabase(final String address, final Pageable pageable) {
        return parkingRepository.findBySimiliarAddress(address, pageable);
    }

    @Transactional
    public ParkingDTO updateParkingInfo(final ParkingDTO parkingDTO, final DecodedUserInfo adminInfo)
            throws ConstraintViolationException, IllegalStateException, IllegalAccessError {

        if (parkingDTO.getId() == null)
            throw new IllegalStateException("Insufficient data...");

        final Parking parkingToUpdate = fetchParkingWithIdFromDatabase(parkingDTO.getId());

        if (!parkingToUpdate.getOwnerId().equals(adminInfo.getUserId())) {
            throw new IllegalAccessError("trying Forbidden access");
        }

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
    public void deleteParkingWithId(final String parkingId, final DecodedUserInfo adminInfo) throws IllegalAccessError {

        final Parking parking = fetchParkingWithIdFromDatabase(parkingId);
        if (!parking.getOwnerId().equals(adminInfo.getUserId())) {
            throw new IllegalAccessError("Forbidden Access");
        }

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

    /**
     * 
     * @param userInfo DecodedUserinfo from the JWT
     * @return a list of parkingDTO owned by userinfo
     */

    public List<ParkingDTO> getListOfMyParkings(final DecodedUserInfo userInfo) {

        return getParkingsOfAdminFromDatabase(userInfo).parallelStream().map(parkingMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * 
     * @param userInfo DecodedUserinfo from the JWT
     * @param status   active status of parkings
     * @return a list of parkingDTO owned by userinfo
     */

    public List<ParkingDTO> getListOfMyParkingsWithStatus(final DecodedUserInfo userInfo, final boolean status) {

        return getParkingsOfAdminFromDatabase(userInfo).parallelStream().filter(parking -> parking.isActive() == status)
                .map(parkingMapper::toDTO).collect(Collectors.toList());
    }

    /**
     * fetch parkings from database
     * 
     * @param userInfo admin details whose parkings needs to be fetched
     * @return list of parking data from the database
     */
    @Transactional(readOnly = true)
    private List<Parking> getParkingsOfAdminFromDatabase(final DecodedUserInfo userInfo) {

        Pageable pageable = PageRequest.of(0, 10);

        return parkingRepository.findAllByOwnerId(userInfo.getUserId(), pageable);
    }

    @Transactional
    public Parking incrementTimesBooked(final String parkingId) {

        final Parking parking = this.fetchParkingWithIdFromDatabase(parkingId);

        parking.setTimeBooked(parking.getTimeBooked() + 1);

        return parkingRepository.save(parking);

    }

}
