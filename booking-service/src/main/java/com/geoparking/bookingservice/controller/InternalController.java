package com.geoparking.bookingservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/internal")
@Slf4j
public class InternalController {

    @GetMapping("/awake")
    public ResponseEntity<?> awakeMe(final HttpServletRequest request) {
        log.info(request.getRemoteAddr() + " Woke me Up");
        return ResponseEntity.ok().build();
    }

}
