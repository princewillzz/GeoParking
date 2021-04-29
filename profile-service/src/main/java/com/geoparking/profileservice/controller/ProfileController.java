package com.geoparking.profileservice.controller;

import com.geoparking.profileservice.dto.ProfileDTO;
import com.geoparking.profileservice.models.AuthenticationRequest;
import com.geoparking.profileservice.models.AuthenticationResponse;
import com.geoparking.profileservice.principal.ProfilePrincipal;
import com.geoparking.profileservice.service.JwtUtilService;
import com.geoparking.profileservice.service.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class ProfileController {

    private final AuthenticationManager authenticationManager;

    private final ProfileService profileService;
    private final JwtUtilService jwtUtilService;

    @Autowired
    public ProfileController(final ProfileService profileService, final JwtUtilService jwtUtilService,
            final AuthenticationManager authenticationManager) {
        this.profileService = profileService;
        this.jwtUtilService = jwtUtilService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Authenticate a user and return a JWT token for further verification
     * 
     * @param authReq
     * @return JWt token
     * @throws Exception
     */
    @PostMapping("/authenticate")
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

    @GetMapping("/profile")
    public ResponseEntity<?> getProfileInfo() {

        final ProfilePrincipal principal = (ProfilePrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return ResponseEntity.ok().body(profileService.loadProfileById(principal.getProfileId()));

    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody final ProfileDTO profileDTO) {

        final ProfilePrincipal principal = (ProfilePrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return ResponseEntity.ok().body(profileService.updateProfileBasicInfo(profileDTO, principal));
    }

    /**
     * Register new user
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerProfile(@RequestBody final ProfileDTO profileDTO) {

        try {
            return ResponseEntity.ok(profileService.createNewProfile(profileDTO));

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {

        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @GetMapping("/user")
    public String user() {
        return "user profile in profile service";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin profile in profile service";
    }

    @GetMapping("/")
    public String base() {
        return "hello";
    }

}
