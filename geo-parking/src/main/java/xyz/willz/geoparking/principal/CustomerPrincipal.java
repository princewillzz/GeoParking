package xyz.willz.geoparking.principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.willz.geoparking.model.Customer;

import java.util.Collection;
import java.util.Collections;

public class CustomerPrincipal implements UserDetails {

    private final Customer customer;

    @Autowired
    public CustomerPrincipal(final Customer customer) {
        this.customer = customer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singleton(
                new SimpleGrantedAuthority("ROLE_CUSTOMER")
        );
    }

    @Override
    public String getPassword() {
        return customer.getPassword();
    }

    @Override
    public String getUsername() {
        return customer.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return customer.getIsActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return customer.getIsActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return customer.getIsActive();
    }

    @Override
    public boolean isEnabled() {
        return customer.getIsActive();
    }
}
