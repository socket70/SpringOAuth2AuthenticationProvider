package com.example.authserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FakeUserDetailsService implements UserDetailsService {
    static Logger logger = LoggerFactory.getLogger(FakeUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("loadUserByUsername({})", username);


        if ("user".equals(username)) {
            return new User("user", "password", Stream.of(new SimpleGrantedAuthority("ROLE_USER")).collect(Collectors.toSet()));
        } else if ("acme".equals(username)) {
            return new User("acme", "acmesecret2", Stream.of(new SimpleGrantedAuthority("ROLE_USER")).collect(Collectors.toSet()));
        }

        throw new UsernameNotFoundException("User not found");
    }
}
