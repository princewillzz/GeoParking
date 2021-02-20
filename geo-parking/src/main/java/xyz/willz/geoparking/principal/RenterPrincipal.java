package xyz.willz.geoparking.principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.willz.geoparking.model.Renter;

import java.util.Collection;
import java.util.Collections;

public class RenterPrincipal implements UserDetails {

    private final Renter renter;

    @Autowired
    public RenterPrincipal(final Renter renter) {
        this.renter = renter;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(
                new SimpleGrantedAuthority("ROLE_RENTER")
        );
    }

    @Override
    public String getPassword() {
        return renter.getPassword();
    }

    @Override
    public String getUsername() {
        return renter.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return renter.getIsActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return renter.getIsActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return renter.getIsActive();
    }

    @Override
    public boolean isEnabled() {
        return renter.getIsActive();
    }
}
