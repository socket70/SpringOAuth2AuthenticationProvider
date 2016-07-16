# Demonstrating how a user can login as a client in Spring QAuth2

This is a simple example of a Spring OAuth2 Authorization Server.

It provides a custom UserDetailsService and ClientDetailsService. They just manually define users and clients, but
serve as an example of how you could pull users and clients from a database.


# The Problem

This example has both a client and a user with the same name - **acme**, but different passwords.

Using the **acme** user, you are able to get a token via either the client_credentials or password grants.

```
$ curl acme:acmesecret2@localhost:8080/oauth/token -d grant_type=password -d client_id=acme -d username=acme -d password=acmesecret2

{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhY21lIiwic2NvcGUiOlsib3BlbmlkIl0sImZvbyI6IkkgYW0gYSB1c2VyIiwiZXhwIjoxNDY4NzQyNDA0LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiMDUyN2VlZWQtZDZjNy00MWQzLWI3NTEtNDg3ZmFmODNiMjhmIiwiY2xpZW50X2lkIjoiYWNtZSJ9.aM0mExY7wLbbTc0MT9wcXjPIebtdah1lRMQPX2Iu3LjdF7OARqSHHNPI66Lev4a8sHhcRobYawpvnTF_Yd0H6xedZfKVCteckGozUH-D9Ho07IIwQfD2_Q1_pVWx6Xh5bIX9wWvMQmaA-Yk0I2lkUgvICafD9qJjKan_1Io82k-5iuMUK0rGK5h3CukjtCIg0tgzSRv2mq88B06kOJxxpWPDyVcml5G9s5W8HmsYvjE-PIIO45o6S5tdzuq2FcO6Ob8sjDNROMZe7jfzAHEL33jJNizAWh1c2IyV1wupZ3IFLtGVZfR2llDkaGE4OG46qJHo87POAj5FDSb62ffZ5A",
  "token_type": "bearer",
  "expires_in": 43199,
  "scope": "openid",
  "foo": "I am a user",
  "jti": "0527eeed-d6c7-41d3-b751-487faf83b28f"
}
```

```
$ curl acme:acmesecret2@localhost:8080/oauth/token -d grant_type=client_credentials

{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJvcGVuaWQiXSwiZm9vIjoiSSBhbSBhIGNsaWVudCIsImV4cCI6MTQ2ODc0NDIwOCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiI0ZmYyZjE0MS0zMzFmLTQzMjUtYmY3Zi1hZmNkNmEzYzIwZDEiLCJjbGllbnRfaWQiOiJhY21lIn0.oUYrdaksi0Ktf-UERauSv4K2lM1rsiytXG7s20ptmns96GfdgcnVwj87lWqR4V09UlUQDBbVdxpey4X3eNfDbqH2Iu307Zo3PMukNe91zdpSUxpA31hpAQNr7sy2hrfqt7mOBOzH_Nr3XXXmNspOkQCLR_TtadpjLQpJ6_QILLZP85_tSy2A8N8C_g4PrZ7i6zDpUn7WvopN0WdFqk3koYftCXGGx7dqDZtIs6bRiBzO_9GbpgDFk2p8U9VbCio3EN_mgnv_No6GzpUdR8Yvxh004E3Ms0keRH70n1Nr5XLnLcJmDLBFg160-Sdg6938q2jM98Gvkwa5oYUd1p4m_g",
  "token_type": "bearer",
  "expires_in": 43199,
  "scope": "openid",
  "foo": "I am a client",
  "jti": "4ff2f141-331f-4325-bf7f-afcd6a3c20d1"
}
```

This poses a security risk because:

* If you accidentally create a user with the same username as a client clientId, they can access the system as that client.
* They are given the same authorities as that client
