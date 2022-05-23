package com.av.finance.account.infrastructure.rest;

import com.av.finance.account.app.rest.RestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class RestClientImpl implements RestClient {

    private final RestTemplate restTemplate;

    public <T> ResponseEntity<T> get(String url, HttpEntity<?> httpEntity,
                                     ParameterizedTypeReference<T> responseType, Object... params) {
        return sendRequest(url, HttpMethod.GET, httpEntity, responseType, params);
    }

    public <T> ResponseEntity<T> post(String url, HttpEntity<?> httpEntity,
                                      ParameterizedTypeReference<T> responseType, Object... params) {
        return sendRequest(url, HttpMethod.POST, httpEntity, responseType, params);
    }

    private <T> ResponseEntity<T> sendRequest(String url, HttpMethod httpMethod, HttpEntity<?> httpEntity,
                                              ParameterizedTypeReference<T> responseType, Object... params) {
        return restTemplate.exchange(url, httpMethod, httpEntity, responseType, params);
    }
}
