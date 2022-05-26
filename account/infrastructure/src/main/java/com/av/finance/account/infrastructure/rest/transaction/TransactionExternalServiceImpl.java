package com.av.finance.account.infrastructure.rest.transaction;

import com.av.finance.account.app.dto.TxDetails;
import com.av.finance.account.app.external.TransactionExternalService;
import com.av.finance.account.common.RequestProperties;
import com.av.finance.account.infrastructure.rest.RestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionExternalServiceImpl implements TransactionExternalService {

    private final RestClient restClient;
    private final RequestProperties requestProperties;

    @Retryable(value = Exception.class, maxAttemptsExpression = "${request.retry-max}",
            backoff = @Backoff(delayExpression = "${request.retry-delay}"))
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

    @Recover
    public List<TxDetails> getTransactionsForAccountsRecover(Exception ex, List<UUID> accountIds) {
        log.error("Transactions call for accounts: {} failed", accountIds, ex);
        return accountIds.stream()
                .map(accountId -> TxDetails.builder()
                        .accountId(accountId)
                        .details("Transactions load failed")
                        .build())
                .collect(Collectors.toList());
    }

    @Retryable(value = Exception.class, maxAttemptsExpression = "${request.retry-max}",
            backoff = @Backoff(delayExpression = "${request.retry-delay}"))
    @Override
    public void createTransaction(TxDetails txDetails) {
        final HttpEntity<TxDetails> httpEntity = new HttpEntity<>(txDetails, getHttpHeaders());
        final ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {};
        final ResponseEntity<String> response = restClient.post(
                requestProperties.getTransactionUrl(), httpEntity, responseType);
        log.info("Initial credit transaction for account: {} successfully processed, response: {}",
                txDetails.getAccountId(), response);
    }

    private HttpHeaders getHttpHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }
}
