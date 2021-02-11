package xyz.willz.geoparking.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "parking", uniqueConstraints = { @UniqueConstraint(columnNames = { "latitude", "longitude" }) })
@Setter
@Getter
public class Parking {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    @Column(name = "address", columnDefinition = "JSON")
    private String address;

    @Column(nullable = false, columnDefinition = "boolean DEFAULT true")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    private Renter renter;

    @PrePersist
    void prePersist() {

        if (this.isActive == null) {
            this.isActive = true;
        }

    }

}
