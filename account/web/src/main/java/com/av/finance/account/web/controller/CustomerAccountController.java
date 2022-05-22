package com.av.finance.account.web.controller;

import com.av.finance.account.app.service.CustomerAccountService;
import com.av.finance.account.web.dto.AccountDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/accounts")
public class CustomerAccountController {

    private final CustomerAccountService customerAccountService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> openAccount(@Valid @RequestBody AccountDetails accountDetails) {
        final UUID accountId = customerAccountService.openAccount(
                accountDetails.getCustomerId(),
                accountDetails.getAccountType(),
                accountDetails.getInitialCredit()
        );
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(accountId)
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
