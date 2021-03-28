package com.geoparking.profileservice.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile {

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private UUID id;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private Date deletedAt;

    @Column(nullable = false)
    private String firstName;
    private String lastName;

    private String profilePicture;

    @Column(nullable = false)
    private String role;

    @Column(unique = true)
    private String mobile;

    @Column(columnDefinition = "boolean default false")
    private boolean isMobileVerified;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(columnDefinition = "boolean default false")
    private boolean isEmailVerified;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(columnDefinition = "boolean default true")
    private Boolean isActive;

    @PrePersist
    void prePersist() {
        this.isActive = true;
    }

}
