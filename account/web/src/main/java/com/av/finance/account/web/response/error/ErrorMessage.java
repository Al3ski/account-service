package com.av.finance.account.web.response.error;

import lombok.Value;

@Value
public class ErrorMessage {

    int code;

    String reason;

    String message;

    String details;
}
