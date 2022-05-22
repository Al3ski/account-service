package com.av.finance.account.infrastructure.rest;

import com.av.finance.account.app.rest.RestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Component
public class RestClientImpl implements RestClient {

    private final RestTemplate restTemplate;
    private final RetryTemplate retryTemplate;

    public <T> ResponseEntity<T> get(String url, HttpEntity<?> httpEntity,
                                     ParameterizedTypeReference<T> responseType, Object... params) {
        return sendRequest(url, HttpMethod.GET, httpEntity, responseType, params);
    }

    public <T> ResponseEntity<T> getRetryable(String url, HttpEntity<?> httpEntity,
                                              ParameterizedTypeReference<T> responseType, Object... params) {
        return sendRetryableRequest(() -> get(url, httpEntity, responseType, params));
    }

    public <T> ResponseEntity<T> post(String url, HttpEntity<?> httpEntity,
                                      ParameterizedTypeReference<T> responseType, Object... params) {
        return sendRequest(url, HttpMethod.POST, httpEntity, responseType, params);
    }

    public <T> ResponseEntity<T> postRetryable(String url, HttpEntity<?> httpEntity,
                                               ParameterizedTypeReference<T> responseType, Object... params) {
        return sendRetryableRequest(() -> post(url, httpEntity, responseType, params));
    }

    private <T> ResponseEntity<T> sendRequest(String url, HttpMethod httpMethod, HttpEntity<?> httpEntity,
                                              ParameterizedTypeReference<T> responseType, Object... params) {
        return restTemplate.exchange(url, httpMethod, httpEntity, responseType, params);
    }

    private <T> T sendRetryableRequest(Supplier<T> requestMethod) {
        return retryTemplate.execute(context -> requestMethod.get());
    }
}
