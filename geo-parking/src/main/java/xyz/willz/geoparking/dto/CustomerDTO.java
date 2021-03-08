package xyz.willz.geoparking.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CustomerDTO {
    private String id;

    private String password;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    private String firstName;
    private String lastName;

    private String profilePicture;

    private String role;

    private String mobile;

    private boolean isMobileVerfied;

    private String email;

    private boolean isEmailVerified;

    private Boolean isActive;

}
