package org.comcom.config.security;

import org.comcom.config.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UsersDetail implements UserDetails {

    private final String username;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;


    public UsersDetail(Users users) {
        this.username = users.getEmail();
        this.password = users.getPassword();
        List<SimpleGrantedAuthority> authorityList = Arrays.stream(users.getProfile().getScopes().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        authorityList.add(new SimpleGrantedAuthority("ROLE_"+users.getProfile().getName().toUpperCase()));
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