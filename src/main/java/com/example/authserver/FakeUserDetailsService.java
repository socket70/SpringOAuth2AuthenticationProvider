package com.example.authserver;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FakeUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if ("user".equals(username)) {
            return new User("user", "password", Stream.of(new SimpleGrantedAuthority("ROLE_USER")).collect(Collectors.toSet()));
        }

        throw new UsernameNotFoundException("User not found");
    }
}
