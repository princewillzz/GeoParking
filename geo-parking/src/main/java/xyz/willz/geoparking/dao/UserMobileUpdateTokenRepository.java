package xyz.willz.geoparking.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.willz.geoparking.model.UserMobileUpdateToken;

public interface UserMobileUpdateTokenRepository extends JpaRepository<UserMobileUpdateToken, UUID> {

}
