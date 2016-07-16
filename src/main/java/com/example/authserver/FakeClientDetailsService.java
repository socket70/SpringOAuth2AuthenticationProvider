package com.example.authserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

@Service
public class FakeClientDetailsService implements ClientDetailsService {
    static Logger logger = LoggerFactory.getLogger(FakeClientDetailsService.class);

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        logger.info("loadClientByClientId({})", clientId);

        if ("acme".equals(clientId)) {
            BaseClientDetails client = new BaseClientDetails("acme", null, "openid", "authorization_code,implicit,password,client_credentials", "ROLE_ADMIN", "http://example.com");
            client.setClientSecret("acmesecret");
            return client;
        }

        throw new NoSuchClientException("Client not found");
    }
}
