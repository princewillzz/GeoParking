package com.geoparking.bookingservice.model;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {

    private UUID id;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    private String firstName;
    private String lastName;

    private String gender;

    private String profilePicture;

    private String role;

    private String mobile;

    private boolean isMobileVerified;

    private String email;

    private boolean isEmailVerified;

    private Boolean isActive;

}
