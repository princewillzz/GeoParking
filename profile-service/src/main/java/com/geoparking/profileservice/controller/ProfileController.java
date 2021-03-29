package com.geoparking.profileservice.controller;


import com.geoparking.profileservice.models.AuthenticationRequest;
import com.geoparking.profileservice.models.AuthenticationResponse;
import com.geoparking.profileservice.service.JwtUtilService;
import com.geoparking.profileservice.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @GetMapping("/auth/hello")
    public String hello() {
        return "Hello world";
    }

    @GetMapping("/auth/user")
    public String user() {
        return "user profile in profile service";
    }

    @GetMapping("/auth/admin")
    public String admin() {
        return "admin profile in profile service";
    }


    @PostMapping("/auth/authenticate")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthenticationRequest authReq) throws Exception {
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorect username or password", e);
        }

        final UserDetails userDetails = profileService.loadUserByUsername(authReq.getUsername());
        String jwt = jwtUtilService.generateToken(userDetails);
        return ResponseEntity.ok().body(new AuthenticationResponse(jwt));

    }

}
