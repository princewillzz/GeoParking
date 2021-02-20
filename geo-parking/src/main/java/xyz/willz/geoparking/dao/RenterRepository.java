package xyz.willz.geoparking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.willz.geoparking.model.Renter;

import java.util.Optional;

public interface RenterRepository extends JpaRepository<Renter, Long> {

    Optional<Renter> findByEmail(String email);

}
