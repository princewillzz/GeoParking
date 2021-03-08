package xyz.willz.geoparking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/renter")
public class RenterController {

    @GetMapping
    @ResponseBody
    public String renterHome() {
        return "Home Of Renter....!";
    }

    @GetMapping("/login")
    public String renterLoginView(Model model) {

        return "/login";
    }

}
