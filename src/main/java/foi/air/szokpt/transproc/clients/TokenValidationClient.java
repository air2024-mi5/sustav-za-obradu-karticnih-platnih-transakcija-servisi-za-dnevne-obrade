package foi.air.szokpt.transproc.clients;

import foi.air.szokpt.transproc.dtos.responses.TokenValidationResponse;
import foi.air.szokpt.transproc.exceptions.ExternalServiceException;
import foi.air.szokpt.transproc.exceptions.TokenValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class TokenValidationClient {
    private final RestTemplate restTemplate;

    @Value("${JwtValidation.api.base.url}")
    private String baseUrl;

    public TokenValidationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        this.restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }
        });
    }

    public String validateToken( String authorizaitionHeader) {
        String url = baseUrl + "/validate-token";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizaitionHeader);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<TokenValidationResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    TokenValidationResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody().getRole();
            }

            if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new TokenValidationException();
            }

        } catch (TokenValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ExternalServiceException("Error occurred while validating token: " + e.getMessage());
        }
        return null;
    }
}

