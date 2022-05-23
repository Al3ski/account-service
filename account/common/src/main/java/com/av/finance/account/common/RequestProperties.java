package com.av.finance.account.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestProperties {

    private String baseUrl;

    private String transactionUrl;

    private long timeout;

    private long readTimeout;

    private int recoverMax;

    private long recoverDelay;
}
