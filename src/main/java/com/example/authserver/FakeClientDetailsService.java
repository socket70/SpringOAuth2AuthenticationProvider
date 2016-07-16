package com.example.authserver;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

public class FakeClientDetailsService implements ClientDetailsService {

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        if ("acme".equals(clientId)) {
            BaseClientDetails client = new BaseClientDetails("acme", null, "openid", "authorization_code,implicit,password,client_credentials", "ROLE_ADMIN", "http://example.com");
            client.setClientSecret("acmesecret");
            return client;
        }

        return null;
    }
}
