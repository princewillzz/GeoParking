package xyz.willz.geoparking.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.willz.geoparking.dao.ParkingRepository;
import xyz.willz.geoparking.dto.ParkingDTO;
import xyz.willz.geoparking.mapper.ParkingMapper;
import xyz.willz.geoparking.model.Parking;
import xyz.willz.geoparking.utilities.ParkingAvailabilityForm;

@Service
@Qualifier("parkingService")
public class ParkingService {

    private final ParkingRepository parkingRepository;

    @Autowired
    protected ParkingService(final ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    // Search all parkings related to a particular address keyword
    @Transactional(readOnly = true)
    public Set<ParkingDTO> searchParkingsForAddress(final String address) {
        return parkingRepository.findAllByAddressContainingIgnoreCase(address).parallelStream()
                .map(parking -> ParkingMapper.INSTANCE.toParkingDTO(parking)).collect(Collectors.toSet());
    }

    // Save a particular parking in the database
    @Transactional
    public Parking saveParking(final ParkingDTO parkingDTO) {

        final Parking parking = ParkingMapper.INSTANCE.toParkingEntity(parkingDTO);

        return parkingRepository.save(parking);
    }

    // Get parking entity for a particular id
    @Transactional(readOnly = true)
    public Parking getParking(final UUID id) {
        return parkingRepository.findById(id).orElseThrow();
    }

    // Get all parking entity
    @Transactional(readOnly = true)
    public List<Parking> getAllparkings() {

        return parkingRepository.findAll();
    }

    // Fetch Popular parkings
    @Transactional(readOnly = true)
    public List<ParkingDTO> getPopularParkings() {

        int size = 6;
        return parkingRepository.findByNoOfTimesBooked(size).parallelStream()
                .map(parking -> ParkingMapper.INSTANCE.toParkingDTO(parking)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    // Check if the booking is available for the current timing
    public boolean isParkingAvailable(final ParkingAvailabilityForm details) throws ParseException {

        // Format to parse input date / time
        final SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-ddHH:mm");

        // parse arrival time
        final Date arrivalDate = sdfInput.parse(details.getArrivalDate()+details.getArrivalTime());
        // parse departure time
        final Date departureDate = sdfInput.parse(details.getDepartureDate() + details.getDepartureTime());
        
        

        return true;
    }

}
