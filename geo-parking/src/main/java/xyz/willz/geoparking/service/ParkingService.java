package xyz.willz.geoparking.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.willz.geoparking.dao.ParkingRepository;
import xyz.willz.geoparking.dto.ParkingDTO;
import xyz.willz.geoparking.mapper.ParkingMapper;
import xyz.willz.geoparking.model.Parking;

@Service
@Qualifier("parkingService")
public class ParkingService {


    private final ParkingRepository parkingRepository;

    private final ApplicationContext applicationContext;

    @Autowired
    protected ParkingService(
        final ParkingRepository parkingRepository,
        final ApplicationContext applicationContext
    ) {
        this.parkingRepository = parkingRepository;

        this.applicationContext = applicationContext;
    }


    // Search all parkings related to a particular address keyword
    @Transactional(readOnly = true)
    public Set<ParkingDTO> searchParkingsForAddress(final String address)  {
        return parkingRepository.findAllByAddressContainingIgnoreCase(address)
                .parallelStream()
                .map(parking -> applicationContext.getBean(ParkingMapper.class).toParkingDTO(parking))
                .collect(Collectors.toSet());
    }



    // Save a particular parking in the database
    @Transactional
    public Parking saveParking(final ParkingDTO parkingDTO) {

        final Parking parking = applicationContext.getBean(ParkingMapper.class).toParkingEntity(parkingDTO);

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

}
