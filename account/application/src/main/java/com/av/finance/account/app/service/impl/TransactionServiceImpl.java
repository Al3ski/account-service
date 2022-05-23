package com.av.finance.account.app.service.impl;

import com.av.finance.account.app.dto.TxDetails;
import com.av.finance.account.app.rest.RestClient;
import com.av.finance.account.app.service.TransactionService;
import com.av.finance.account.common.RequestProperties;
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
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final RestClient restClient;
    private final RequestProperties requestProperties;

    @Override
    public List<TxDetails> getTransactionsForAccounts(List<UUID> accountIds) {
        final HttpEntity<List<UUID>> httpEntity = new HttpEntity<>(getHttpHeaders());
        final String uri = UriComponentsBuilder.fromUriString(requestProperties.getTransactionUrl())
                .queryParam("account_id", accountIds)
                .build()
                .toUriString();
        final ParameterizedTypeReference<List<TxDetails>> responseType = new ParameterizedTypeReference<List<TxDetails>>() {};
        final ResponseEntity<List<TxDetails>> response = restClient.get(uri, httpEntity, responseType);
        log.info("Request transactions for accounts: {} processed, response: {}", accountIds, response);
        return response.getBody();
    }

    @Override
    public List<TxDetails> getTransactionsForAccountsRecover(Exception ex, List<UUID> accountIds) {
        log.error("Transactions call for accounts: {} failed: {}", accountIds, ex);
        return accountIds.stream()
                .map(accountId -> new TxDetails(null, accountId, null, null,
                        "Transactions load failed"))
                .collect(Collectors.toList());
    }

    @Override
    public void createTransaction(UUID accountId, TransactionType type,
                                  BigDecimal initialCredit, String details) {
        final TxDetails txDetails = new TxDetails(null, accountId, type, initialCredit, details);
        final HttpEntity<TxDetails> httpEntity = new HttpEntity<>(txDetails, getHttpHeaders());
        final ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {};
        final ResponseEntity<String> response = restClient.post(
                requestProperties.getTransactionUrl(), httpEntity, responseType);
        log.info("Initial credit transaction for account: {} successfully processed, response: {}", accountId, response);
    }

    private HttpHeaders getHttpHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
