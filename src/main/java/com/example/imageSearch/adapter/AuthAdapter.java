package com.example.imageSearch.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class AuthAdapter {

    @Value("${api.images.auth.link}")
    public String authLink;

    @Value("${api.key}")
    public String apiKey;

    RestTemplate rest = new RestTemplate();
    Logger log = LoggerFactory.getLogger(AuthAdapter.class);

    public String getToken() {
        log.debug("Get new token for apiKey : " + apiKey);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(authLink);

        String requestJson = "{\"apiKey\":\"" + apiKey + "\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        JsonNode response = rest.postForObject(builder.build().encode().toUri(), entity, JsonNode.class);
        boolean auth = response != null && response.get("auth").asBoolean();
        if (auth) {
            String token = response.get("token").asText();
            log.debug("New bearer token is: " + token);
            return token;
        }

        log.error("Token auth status is not true");
        throw new HttpClientErrorException(HttpStatus.EXPECTATION_FAILED, "Auth response status is false: " + response.asText());
    }

}
