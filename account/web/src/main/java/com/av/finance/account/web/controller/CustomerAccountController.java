package com.av.finance.account.web.controller;

import com.av.finance.account.app.dto.AccountDetails;
import com.av.finance.account.app.service.CustomerAccountService;
import com.av.finance.account.web.dto.AccountInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Account", description = "Customer Account related APIs")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/accounts")
public class CustomerAccountController {

    private final CustomerAccountService customerAccountService;

    @Operation(
            summary = "Get accounts details by customer ID",
            description = "Return accounts details for certain customer ID or all customers"
    )
    @GetMapping(path = "/details", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountDetails>> getAccountsDetails(@Valid
                                                                   @RequestParam(name = "customer_id", required = false)
                                                                   UUID customerId) {
        final List<AccountDetails> accountsDetails = customerAccountService.getCustomerAccountsDetails(customerId);
        return ResponseEntity.ok(accountsDetails);
    }

    @Operation(
            summary = "Create new customer account",
            description = "Return location of the newly created account"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    headers = @Header(
                            name = "location",
                            description = "Link to newly created account",
                            schema = @Schema(type = "string")
                    ))
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> openAccount(@Valid @RequestBody AccountInput accountInput) {
        final UUID accountId = customerAccountService.openAccount(
                accountInput.getCustomerId(),
                accountInput.getAccountType(),
                accountInput.getInitialCredit()
        );
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(accountId)
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
