package com.geoparking.profileservice.repository;

import java.util.Optional;
import java.util.UUID;

import com.geoparking.profileservice.entity.Profile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    Optional<Profile> findByEmail(String email);
}
