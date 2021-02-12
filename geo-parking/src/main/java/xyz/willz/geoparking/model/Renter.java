package xyz.willz.geoparking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "renter")
@Getter
@Setter
public class Renter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String firstName;
    private String middleName;
    private String lastName;

    @Column(nullable = false, unique = true)
    private String mobile;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private Boolean isActive;

    @PrePersist
    void prePersist() {

        if (this.isActive == null) {
            this.isActive = true;
        }

    }

}
