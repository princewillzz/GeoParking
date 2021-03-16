package xyz.willz.geoparking.controller.api_controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import xyz.willz.geoparking.dto.BookingDTO;
import xyz.willz.geoparking.dto.CustomerDTO;
import xyz.willz.geoparking.model.Customer;
import xyz.willz.geoparking.service.CustomerService;

@RestController
@RequestMapping("/api/")
@Slf4j
@AllArgsConstructor
public class CustomerControllerAPI {

    private final CustomerService customerService;

    @GetMapping(value = "/secured")
    public boolean isSomeOneLoggedIn() {

        System.err.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return true;
    }

    @PutMapping(value = "/secured/user/profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody CustomerDTO customerDTO) {

        try {
            var principal = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            customerDTO.setId(principal.getSubject());

            customerService.updateCustomer(customerDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/secured/my-bookings")
    public ResponseEntity<List<BookingDTO>> getAllMyBookings() {

        var userDetails = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok().body(customerService.getAllBookingsForCustomer(new Customer(userDetails)));

    }

    @GetMapping("/secured/customer/mobile/sendtoken")
    public ResponseEntity<?> sendTokenToMail(@RequestParam(value = "mobile", required = true) String mobile) {

        System.out.println(mobile);

        final DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        customerService.sendtokenToUpdateMobile(principal.getSubject(), mobile);

        return ResponseEntity.ok().build();
    }

}
