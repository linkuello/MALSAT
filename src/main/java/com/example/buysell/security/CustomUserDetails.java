package com.example.buysell.security;

import com.example.buysell.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // Или user.getRole().getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // Или user.getUsername(), если идентификатор — это username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Можно добавить логику проверки истечения аккаунта
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Можно добавить логику блокировки аккаунта
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Можно добавить логику истечения пароля
    }

    @Override
    public boolean isEnabled() {
        return user.isVerified(); // Или просто return true;
    }
}
