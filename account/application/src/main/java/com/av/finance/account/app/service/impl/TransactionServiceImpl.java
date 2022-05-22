package com.av.finance.account.app.service.impl;

import com.av.finance.account.app.dto.TxDetails;
import com.av.finance.account.app.rest.RestClient;
import com.av.finance.account.app.service.TransactionService;
import com.av.finance.account.domain.transaction.Transaction;
import com.av.finance.account.domain.transaction.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final String transactionUri = "/v1/transactions";

    private final RestClient restClient;

    @Override
    public List<TxDetails> getTransactionsForAccounts(List<UUID> accountIds) {
        final HttpEntity<List<UUID>> httpEntity = new HttpEntity<>(getHttpHeaders());
        final String uri = UriComponentsBuilder.fromUriString(transactionUri)
                .queryParam("account_id", accountIds)
                .build()
                .toUriString();
        final ParameterizedTypeReference<List<TxDetails>> responseType = new ParameterizedTypeReference<List<TxDetails>>() {};
        final ResponseEntity<List<TxDetails>> response = restClient.getRetryable(uri, httpEntity, responseType);
        log.info("Request transactions for accounts: {} processed, response: {}", accountIds, response);
        return response.getBody();
    }

    @Override
    public void createTransaction(UUID accountId, TransactionType type,
                                  BigDecimal initialCredit, String details) {
        final TxDetails txDetails = new TxDetails(null, accountId, type, initialCredit, details);
        final HttpEntity<TxDetails> httpEntity = new HttpEntity<>(txDetails, getHttpHeaders());
        final ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {};
        final ResponseEntity<String> response = restClient.postRetryable(transactionUri, httpEntity,  responseType);
        log.info("Initial credit transaction for account: {} successfully processed, response: {}", accountId, response);
    }

    private HttpHeaders getHttpHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
