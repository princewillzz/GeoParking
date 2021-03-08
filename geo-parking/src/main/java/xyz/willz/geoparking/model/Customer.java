package xyz.willz.geoparking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {

    @Id
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private String id;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private Date deletedAt;

    @Column(nullable = false)
    private String firstName;
    private String lastName;

    private String profilePicture;

    private String role;

    @Column(unique = true)
    private String mobile;

    @Column(columnDefinition = "boolean default false")
    private boolean isMobileVerfied;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(columnDefinition = "boolean default false")
    private boolean isEmailVerified;

    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    private Boolean isActive;

    @PrePersist
    void prePersist() {

        this.isActive = true;

    }

    public Customer() {
    }

    public Customer(final DefaultOidcUser userDetails) {
        this.id = userDetails.getSubject();
        this.firstName = userDetails.getGivenName();
        this.lastName = userDetails.getFamilyName();
        this.email = userDetails.getEmail();
        this.isEmailVerified = userDetails.getEmailVerified();
        this.role = "ROLE_USER";
        this.profilePicture = userDetails.getPicture();
    }

}