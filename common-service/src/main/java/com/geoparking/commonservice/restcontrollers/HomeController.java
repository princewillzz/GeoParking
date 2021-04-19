package com.geoparking.commonservice.restcontrollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String test() {
        return "slow";
    }

    @GetMapping("slow")
    public String slowlyCalling() throws InterruptedException {

        double time = Math.random() * 4 + 2;

        Thread.sleep((long) (time * 1000));

        return "Slowly slowly" + time;
    }

}
