# Creating a custom AuthenticationProvider in a Spring OAuth2 Authorization Server

This is a working example of a simple OAuth2 Authorization Server. It includes a custom AuthorizationProvider.

The problem is, the provider doesn't seem to get involved in all authenication requests.

| # | grant             | client_id | secret | username | password | auth provider invoked |
|---|-------------------|-----------|--------|----------|----------|-----------------------|
| 1 | client_credentials| good      | good   |          |          | no                    |
| 2 | client_credentials| bad       | good   |          |          | no                    |
| 3 | client_credentials| good      | bad    |          |          | yes                   |
| 4 | password          | good      | good   | good     | good     | yes                   |
| 5 | password          | good      | good   | bad      | good     | yes                   |
| 6 | password          | good      | good   | good     | bad      | yes                   |
| 7 | password          | bad       | good   | good     | good     | no                    |


> 1. `curl acme:acmesecret@localhost:8080/oauth/token -d grant_type=client_credentials`
> 2. `curl acme2:acmesecret@localhost:8080/oauth/token -d grant_type=client_credentials`
> 3. `curl acme:acmesecret2@localhost:8080/oauth/token -d grant_type=client_credentials`
> 4. `curl acme:acmesecret@localhost:8080/oauth/token -d grant_type=password -d client_id=acme -d username=user -d password=password`
> 5. `curl acme:acmesecret@localhost:8080/oauth/token -d grant_type=password -d client_id=acme -d username=user2 -d password=password`
> 6. `curl acme:acmesecret@localhost:8080/oauth/token -d grant_type=password -d client_id=acme -d username=user -d password=pass`
> 7. `curl acme2:acmesecret@localhost:8080/oauth/token -d grant_type=password -d client_id=acme -d username=user -d password=password`

The second problem is, when the custom AuthenticationProvider is invoked, it is invoked for both clients and users and
as far as I can tell there is no way to determine if the request credentials are a client or a user.