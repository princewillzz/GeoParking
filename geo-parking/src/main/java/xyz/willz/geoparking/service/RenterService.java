package xyz.willz.geoparking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.willz.geoparking.dao.RenterRepository;
import xyz.willz.geoparking.dto.RenterDTO;
import xyz.willz.geoparking.mapper.RenterMapper;
import xyz.willz.geoparking.model.Renter;

@Service
@Qualifier("renterService")
public class RenterService {

    private final RenterRepository renterRepository;
    
    private final RenterMapper renterMapper;

    @Autowired
    protected RenterService(
        final RenterRepository renterRepository,
        final RenterMapper renterMapper
    ) {
        this.renterRepository = renterRepository;
        this.renterMapper = renterMapper;
    }


    @Transactional(readOnly = true)
    public Renter getRenter(final long id) {

        return renterRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<Renter> getAllRenter() {

        return renterRepository.findAll();
    }

    @Transactional
    public Renter createRenter(final RenterDTO renterDTO) {

        final Renter renter = renterMapper.toRenterEntity(renterDTO);
        
        return renterRepository.save(renter);
    }

    @Transactional
    public Renter updateRenter(final RenterDTO renterDTO) {

        final Renter renter = renterMapper.toRenterEntity(renterDTO);

        return renterRepository.save(renter);
    }


}
