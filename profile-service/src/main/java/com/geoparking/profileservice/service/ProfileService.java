package com.geoparking.profileservice.service;

import java.util.UUID;

import javax.validation.ConstraintViolationException;

import com.geoparking.profileservice.dto.ProfileDTO;
import com.geoparking.profileservice.entity.Profile;
import com.geoparking.profileservice.principal.ProfilePrincipal;
import com.geoparking.profileservice.repository.ProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService implements UserDetailsService {
    private final ProfileRepository profileRepository;

    private final UtilityService utilityService;

    @Autowired
    public ProfileService(final ProfileRepository profileRepository, final UtilityService utilityService) {
        this.profileRepository = profileRepository;
        this.utilityService = utilityService;
    }

    /**
     * Load profiles by id (surrogate key of the profile)
     * 
     * @param profileId
     * @return profile entitity
     * @throws UsernameNotFoundException
     */
    public Profile loadProfileById(final String profileId) throws UsernameNotFoundException {

        return profileRepository.findById(UUID.fromString(profileId))
                .orElseThrow(() -> new UsernameNotFoundException("Unable To find User"));

    }

    @Transactional
    public ProfileDTO createNewProfile(final ProfileDTO profileDTO)
            throws ConstraintViolationException, IllegalArgumentException {

        // Copy profile data fron profile DTO to profile entity
        final Profile profile = utilityService.copyDetailsRequiredToRegisterAndValidateProfile(profileDTO);

        profileRepository.save(profile);

        return profileDTO;
    }

    @Override
    public ProfilePrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        return new ProfilePrincipal(
                profileRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User 404")));
    }

    public ProfilePrincipal loadUserBySubject(String subject) throws UsernameNotFoundException {
        return new ProfilePrincipal(profileRepository.findById(UUID.fromString(subject))
                .orElseThrow(() -> new UsernameNotFoundException("User 404")));
    }

    @Transactional
    public Profile updateProfileBasicInfo(final ProfileDTO profileDTO, final ProfilePrincipal principal)
            throws UsernameNotFoundException, ConstraintViolationException {

        System.err.println(profileDTO);

        // Get Current User
        final Profile profile = this.loadProfileById(principal.getProfileId());

        // change data in the modal

        utilityService.copyBasicDetailsAndValidate(profileDTO, profile);

        return profile;
    }

}
