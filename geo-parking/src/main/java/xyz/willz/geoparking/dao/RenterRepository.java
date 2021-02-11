package xyz.willz.geoparking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.willz.geoparking.model.Renter;

public interface RenterRepository extends JpaRepository<Renter, Long> {

}
