package xyz.willz.geoparking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.willz.geoparking.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
