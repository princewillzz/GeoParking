package com.geoparking.profileservice.service;


import com.geoparking.profileservice.principal.ProfilePrincipal;
import com.geoparking.profileservice.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProfileService implements UserDetailsService {
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new ProfilePrincipal(profileRepository.findByEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User 404")
                ));
    }
}
