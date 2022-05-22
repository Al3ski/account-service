package com.av.finance.account.app.rest;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public interface RestClient {

    <T> ResponseEntity<T> get(String url, HttpEntity<?> httpEntity, ParameterizedTypeReference<T> responseType, Object... params);

    <T> ResponseEntity<T> getRetryable(String url, HttpEntity<?> httpEntity, ParameterizedTypeReference<T> responseType, Object... params);

    <T> ResponseEntity<T> post(String url, HttpEntity<?> httpEntity, ParameterizedTypeReference<T> responseType, Object... params);

    <T> ResponseEntity<T> postRetryable(String url, HttpEntity<?> httpEntity, ParameterizedTypeReference<T> responseType, Object... params);
}
