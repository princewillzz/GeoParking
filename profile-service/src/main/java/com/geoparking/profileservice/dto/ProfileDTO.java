package com.geoparking.profileservice.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {

    private String id;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("lastname")
    private String lastName;

    private String gender;

    private String profilePicture;

    private String role;

    private String mobile;

    private boolean isMobileVerified;

    private String email;

    private boolean isEmailVerified;

    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    private Boolean isActive;

}
