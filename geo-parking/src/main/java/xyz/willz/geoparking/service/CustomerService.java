package xyz.willz.geoparking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import xyz.willz.geoparking.dao.CustomerRepository;
import xyz.willz.geoparking.dto.BookingDTO;
import xyz.willz.geoparking.dto.CustomerDTO;
import xyz.willz.geoparking.mapper.CustomerMapper;
import xyz.willz.geoparking.model.Booking;
import xyz.willz.geoparking.model.Customer;

@Service
@Slf4j
@Qualifier("customerService")
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    private final ApplicationContext applicationContext;

    @Transactional(readOnly = true)
    public Customer getCustomer(final String id) throws UsernameNotFoundException {
        return customerRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not present"));
    }

    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer createCustomer(final CustomerDTO customerDTO) {
        final Customer customer = customerMapper.toCustomerEntity(customerDTO);

        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateCustomer(final CustomerDTO customerDTO) {

        final var customer = this.getCustomer(customerDTO.getId());
        final var firstName = customerDTO.getFirstName();
        final var lastName = customerDTO.getLastName();

        if (firstName != null && !firstName.isBlank()) {
            customer.setFirstName(firstName);
        }
        if (lastName != null && !lastName.isBlank()) {
            customer.setLastName(customerDTO.getLastName());

        }

        return customer;
    }

    @Transactional(noRollbackFor = { UsernameNotFoundException.class })
    public Customer saveCustomerDetail(final DefaultOidcUser userDetails) {

        final Customer customer = new Customer(userDetails);

        try {
            return this.getCustomer(customer.getId());
        } catch (Exception e) {
            log.info("New User found");
        }

        return customerRepository.save(customer);

    }

    public List<Booking> getAllBookingsForCustomer(final Customer customer) {

        return applicationContext.getBean(BookingService.class).bookingsForCustomer(customer);
    }

}
