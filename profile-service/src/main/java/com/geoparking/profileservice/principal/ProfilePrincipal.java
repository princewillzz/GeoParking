package com.geoparking.profileservice.principal;

import com.geoparking.profileservice.entity.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class ProfilePrincipal implements UserDetails {
    private static final long serialVersionUID = 1L;

    private final Profile profile;

    public ProfilePrincipal(final Profile profile) {
        this.profile = profile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(profile.getRole()));
    }

    @Override
    public String getPassword() {
        return profile.getPassword();
    }

    @Override
    public String getUsername() {
        return profile.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return profile.getIsActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return profile.getIsActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return profile.getIsActive();
    }

    @Override
    public boolean isEnabled() {
        return profile.getIsActive();
    }
}
