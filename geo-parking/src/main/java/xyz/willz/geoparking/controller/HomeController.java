package xyz.willz.geoparking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String home() {

        return "index";
    }

    @GetMapping(value = "test")
    @ResponseBody
    public String test() {
        return "Test";
    }

    @GetMapping("/login")
    public String userLoginView(Model modal) {

        return "login";
    }

}
