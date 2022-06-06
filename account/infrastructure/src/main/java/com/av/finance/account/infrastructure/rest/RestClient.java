package com.av.finance.account.infrastructure.rest;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public interface RestClient {

    <T> ResponseEntity<T> get(String url, HttpEntity<?> httpEntity, ParameterizedTypeReference<T> responseType, Object... params);

    <T> ResponseEntity<T> post(String url, HttpEntity<?> httpEntity, ParameterizedTypeReference<T> responseType, Object... params);
}
