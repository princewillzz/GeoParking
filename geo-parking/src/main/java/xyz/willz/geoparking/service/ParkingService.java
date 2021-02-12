package xyz.willz.geoparking.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.willz.geoparking.dao.ParkingRepository;
import xyz.willz.geoparking.model.Parking;

@Service
@Qualifier("parkingService")
public class ParkingService {


    private final ParkingRepository parkingRepository;

    @Autowired
    protected ParkingService(
        final ParkingRepository parkingRepository
    ) {
        this.parkingRepository = parkingRepository;
    }


    @Transactional(readOnly = true)
    public Parking getParking(final UUID id) {
        return parkingRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<Parking> getAllparkings() {

        return parkingRepository.findAll();    
    }

}
