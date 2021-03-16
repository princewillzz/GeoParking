package xyz.willz.geoparking.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import xyz.willz.geoparking.dao.CustomerRepository;
import xyz.willz.geoparking.dao.UserMobileUpdateTokenRepository;
import xyz.willz.geoparking.dto.BookingDTO;
import xyz.willz.geoparking.dto.CustomerDTO;
import xyz.willz.geoparking.dto.ParkingDTO;
import xyz.willz.geoparking.mapper.BookingMapper;
import xyz.willz.geoparking.mapper.CustomerMapper;
import xyz.willz.geoparking.mapper.ParkingMapper;
import xyz.willz.geoparking.model.Booking;
import xyz.willz.geoparking.model.Customer;
import xyz.willz.geoparking.model.Parking;
import xyz.willz.geoparking.model.UserMobileUpdateToken;

@Service
@Slf4j
@Qualifier("customerService")
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserMobileUpdateTokenRepository userMobileUpdateTokenRepository;

    private final CustomerMapper customerMapper;

    private final ApplicationContext applicationContext;

    private final MailService mailService;

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

    public List<BookingDTO> getAllBookingsForCustomer(final Customer customer) {

        final List<Booking> listOfBookings = applicationContext.getBean(BookingService.class)
                .bookingsForCustomer(customer);

        final List<BookingDTO> listOfBookingDTOs = listOfBookings.stream().map(booking -> {
            final BookingDTO bookingDTO = BookingMapper.INSTANCE.toBookingDTO(booking);

            final ParkingDTO parkingDTO = ParkingMapper.INSTANCE.toParkingDTO(booking.getParking());

            bookingDTO.setParking(parkingDTO);

            return bookingDTO;
        }).collect(Collectors.toList());

        return listOfBookingDTOs;
    }

    public void sendtokenToUpdateMobile(final String customerId, String mobile) {

        final Customer customer = this.getCustomer(customerId);

        String token = this.saveTokenToUpdateUserMobile(customer, mobile);

        final StringBuffer body = new StringBuffer()

                .append("<html><body><a>http://localhost:8080/customer/update?mobile=").append(mobile)
                .append("&&token=").append(token).append("</a>").append("<h2>Click on the link to update mobile</h2>")
                .append("</body></html>");

        mailService.sendEmail(customer.getEmail(), "Geo Parking update mobile", body.toString());

    }

    @Transactional
    private String saveTokenToUpdateUserMobile(final Customer customer, final String mobile) {
        final UserMobileUpdateToken token = new UserMobileUpdateToken();
        token.setCustomer(customer);
        token.setMobile(mobile);

        return userMobileUpdateTokenRepository.save(token).getId().toString();

    }

    @Transactional
    public Customer updateCustomerMobile(final String customerId, final String token, final String mobile) {

        final UserMobileUpdateToken tokenEntity = userMobileUpdateTokenRepository.findById(UUID.fromString(token))
                .orElseThrow();

        if (tokenEntity.getExpirationTime().before(new Date(System.currentTimeMillis()))) {
            throw new RuntimeException("Token Expired");
        }

        final Customer customer = tokenEntity.getCustomer();

        if (customer.getId().equals(customerId) && tokenEntity.getMobile().equals(mobile)) {
            customer.setMobile(mobile);
        } else {
            throw new RuntimeException("Tampered url");
        }

        return customer;
    }

}
