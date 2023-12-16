package org.comcom.config.security;

import lombok.Getter;
import org.comcom.model.Role;
import org.comcom.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DefaultUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final boolean isVerified;
    private final Role profile;
    private final List<SimpleGrantedAuthority> authorities;

    public DefaultUserDetails(Users users) {
        this.username = users.getEmail();
        this.password = users.getPassword();
        this.isVerified = users.getVerified();
        this.profile = users.getProfile();
        List<SimpleGrantedAuthority> authorityList = Arrays.stream(users.getProfile().getScopes().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        authorityList.add(new SimpleGrantedAuthority("ROLE_" + users.getProfile().getName().toUpperCase()));
        this.authorities = authorityList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
