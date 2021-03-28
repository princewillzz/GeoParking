package com.geoparking.commonservice.restcontrollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String test() {
        return "public: user: admin";
    }

    @GetMapping(value = "public")
    public String publictest() {
        return "Public";
    }

    @GetMapping(value = "/auth/user")
    public String privatetest() {
        return "user";
    }

    @GetMapping(value = "/auth/admin")
    public String admintest() {
        return "admin";
    }

    @GetMapping("slow")
    public String slowlyCalling() throws InterruptedException {

        double time = Math.random() * 4 + 2;

        Thread.sleep((long) (time * 1000));

        return "Slowly slowly" + time;
    }

}
