package com.geoparking.profileservice.service;

import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;

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

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile loadProfileById(final String profileId) throws UsernameNotFoundException {

        return profileRepository.findById(UUID.fromString(profileId))
                .orElseThrow(() -> new UsernameNotFoundException("Unable To find User"));

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
        this.copyBasicDetailsAndValidate(profileDTO, profile);

        return profile;
    }

    /**
     * Copy basic information from DTO to profile to be converted
     * 
     * @param profileDTO data to be copied from
     * @param profile    data to be copied to
     */
    private void copyBasicDetailsAndValidate(final ProfileDTO profileDTO, final Profile profile) {
        profile.setFirstName(profileDTO.getFirstName());
        profile.setLastName(profileDTO.getLastName());
        profile.setGender(profileDTO.getGender());

        validateProfileModel(profile);
    }

    /**
     * Validate the profile modal according to the contrainsts provided
     * 
     * @param profile the entity to be checked validations of
     * @throws ConstraintViolationException
     */
    private void validateProfileModel(final Profile profile) throws ConstraintViolationException {
        Set<ConstraintViolation<Profile>> violations = Validation.buildDefaultValidatorFactory().getValidator()
                .validate(profile);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }
}
