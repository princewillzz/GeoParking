package xyz.willz.geoparking.controller.api_controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;
import xyz.willz.geoparking.service.BookingService;

@RestController
@Slf4j
public class BookingControllerAPI {


    private final BookingService bookingService;

    @Autowired
    protected BookingControllerAPI(
            @Qualifier("bookingService") final BookingService bookingService
    ) {
        this.bookingService = bookingService;
    }





}
