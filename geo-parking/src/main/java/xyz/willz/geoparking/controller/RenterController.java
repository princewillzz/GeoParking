package xyz.willz.geoparking.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/renter")
public class RenterController {


    @GetMapping("/login")
    public String renterLoginView(Model model ) {

        return "renter/login";
    }

}
