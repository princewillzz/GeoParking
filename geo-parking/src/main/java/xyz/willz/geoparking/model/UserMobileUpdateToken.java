package xyz.willz.geoparking.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserMobileUpdateToken {

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    @CreationTimestamp
    private Date createdAt;

    private String mobile;

    private final Date expirationTime;

    public UserMobileUpdateToken() {
        expirationTime = new Date(System.currentTimeMillis() + (10 * 60 * 1000));
    }

    public void setMobile(String mobile) {
        for (int index = 0; index < mobile.length(); index++) {
            if (mobile.charAt(index) < '0' || mobile.charAt(index) > '9') {
                throw new IllegalStateException("Invalid mobile Number");
            }

        }

        this.mobile = mobile;

    }

}
