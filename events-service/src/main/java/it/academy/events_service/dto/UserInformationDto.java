package it.academy.events_service.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class UserInformationDto implements UserDetails {
    private Set<SimpleGrantedAuthority> authoritySet;
    private String username;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authoritySet;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public Set<SimpleGrantedAuthority> getAuthoritySet() {
        return authoritySet;
    }

    public void setAuthoritySet(Set<SimpleGrantedAuthority> authoritySet) {
        this.authoritySet = authoritySet;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
