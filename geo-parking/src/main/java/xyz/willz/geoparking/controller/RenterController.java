package xyz.willz.geoparking.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import xyz.willz.geoparking.service.ParkingService;

@Controller
@RequestMapping(value = "/renter")
@Slf4j
@AllArgsConstructor
public class RenterController {

    private final ParkingService parkingService;

    @GetMapping
    public String renterHome() {
        return "/renter/dashboard";
    }

    @GetMapping("/login")
    public String renterLoginView(Model model) {

        return "/login";
    }

    @GetMapping("/parking/edit")
    public ModelAndView parkingEditView(@RequestParam("id") final String parkingId) {
        final ModelAndView modelAndView = new ModelAndView("/renter/parking_edit_info");

        try {
            parkingService.getParking(UUID.fromString(parkingId));

        } catch (Exception e) {
            log.error(e.getMessage());
            modelAndView.setViewName("redirect:/renter");
        }

        return modelAndView;
    }

}
