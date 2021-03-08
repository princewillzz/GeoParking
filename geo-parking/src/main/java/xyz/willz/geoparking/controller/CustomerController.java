package xyz.willz.geoparking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerController {

    @GetMapping(value = "/customer/my-bookings")
    public ModelAndView myBookingsView() {
        return new ModelAndView("my-bookings");
    }

}
