package com.example.authserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class MyAuthenticationProvider extends DaoAuthenticationProvider {
    static Logger logger = LoggerFactory.getLogger(MyAuthenticationProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            logger.info("-----------------------------------------------------------------");
            logger.info("Authentication attempt for {}", authentication.getPrincipal());
            Authentication auth = super.authenticate(authentication);
            logger.info("Authentication successful");
            return auth;

        } catch (BadCredentialsException e) {
            logger.warn("Authentication failed - Bad credentials");
            throw e;

        } catch (AuthenticationException e) {
            logger.warn("Authentication failed - {}", e.toString());
            throw e;
        }
    }
}
