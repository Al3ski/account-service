package com.av.finance.account.app.rest;

import org.springframework.http.HttpEntity;

public interface RestClient {

    <T> T get(String url, HttpEntity<?> httpEntity, Class<T> responseType, Object... params);

    <T> T getRetryable(String url, HttpEntity<?> httpEntity, Class<T> responseType, Object... params);

    <T> T post(String url, HttpEntity<?> httpEntity, Class<T> responseType);

    <T> T postRetryable(String url, HttpEntity<?> httpEntity, Class<T> responseType);
}
