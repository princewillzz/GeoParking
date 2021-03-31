package com.geoparking.parkingservice.repository;

import com.geoparking.parkingservice.model.Parking;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ParkingRepository extends MongoRepository<Parking, String> {
}
