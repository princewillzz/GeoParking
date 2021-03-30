package com.geoparking.gatewayserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackMethodController {

    @RequestMapping(value = "/common-service", method = { RequestMethod.GET, RequestMethod.POST })
    public String commonServiceFallBackMethod() {
        return "Common Service is down please Try later...!";
    }

}
