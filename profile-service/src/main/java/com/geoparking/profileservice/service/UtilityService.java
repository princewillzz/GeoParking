package com.geoparking.profileservice.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;

import com.geoparking.profileservice.dto.ProfileDTO;
import com.geoparking.profileservice.entity.Profile;

import org.springframework.stereotype.Service;

@Service
final public class UtilityService {

    /**
     * Copy details required only for registration purpose
     * 
     * @param profileDTO
     * @return profile DTO
     * @throws ConstraintViolationException
     */

    Profile copyDetailsRequiredToRegisterAndValidateProfile(final ProfileDTO profileDTO)
            throws ConstraintViolationException, IllegalArgumentException {

        final Profile profile = new Profile();

        // Required profiles
        profile.setEmail(profileDTO.getEmail());
        profile.setPassword(profileDTO.getPassword());
        if (profileDTO.getRole().equalsIgnoreCase("admin")) {
            profile.setRole("ROLE_ADMIN");
        } else if (profileDTO.getRole().equalsIgnoreCase("user")) {
            profile.setRole("ROLE_USER");
        } else {
            throw new IllegalArgumentException("Role is ambiguos");
        }

        // changing role name just in case
        profileDTO.setRole(profile.getRole());

        profile.setFirstName(profileDTO.getFirstName());
        profile.setLastName(profileDTO.getLastName());

        validateProfileModel(profile);

        return profile;

    }

    /**
     * Copy basic information from DTO to profile to be converted
     * 
     * @param profileDTO data to be copied from
     * @param profile    data to be copied to
     */
    void copyBasicDetailsAndValidate(final ProfileDTO profileDTO, final Profile profile) {
        profile.setFirstName(profileDTO.getFirstName());
        profile.setLastName(profileDTO.getLastName());
        profile.setGender(profileDTO.getGender());

        this.validateProfileModel(profile);
        // validateProfileModel(profile);
    }

    /**
     * Validate the profile modal according to the contrainsts provided
     * 
     * @param profile the entity to be checked validations of
     * @throws ConstraintViolationException
     */
    void validateProfileModel(final Profile profile) throws ConstraintViolationException {
        Set<ConstraintViolation<Profile>> violations = Validation.buildDefaultValidatorFactory().getValidator()
                .validate(profile);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

}
