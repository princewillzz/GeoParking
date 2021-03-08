package xyz.willz.geoparking.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.willz.geoparking.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    Optional<Customer> findByEmail(String email);
}
