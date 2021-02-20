package xyz.willz.geoparking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.willz.geoparking.dao.CustomerRepository;
import xyz.willz.geoparking.dto.CustomerDTO;
import xyz.willz.geoparking.mapper.CustomerMapper;
import xyz.willz.geoparking.model.Customer;
import xyz.willz.geoparking.principal.CustomerPrincipal;

@Service
@Qualifier("customerService")
public class CustomerService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Autowired
    protected CustomerService(
        final CustomerRepository customerRepository,
        final CustomerMapper customerMapper
    ) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }


    @Transactional(readOnly = true)
    public Customer getCustomer(final long id) {
        return customerRepository.findById(id).orElseThrow();
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
        final Customer customer = customerMapper.toCustomerEntity(customerDTO);

        return customerRepository.save(customer);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not available"));

        return new CustomerPrincipal(customer);

    }
}
