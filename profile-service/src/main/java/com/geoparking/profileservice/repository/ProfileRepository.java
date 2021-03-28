package com.geoparking.profileservice.repository;

import com.geoparking.profileservice.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {


    Optional<Profile> findByEmail(String email);
}
