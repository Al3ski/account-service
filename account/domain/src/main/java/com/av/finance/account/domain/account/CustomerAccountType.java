package com.av.finance.account.domain.account;

import org.apache.commons.text.CaseUtils;

public enum CustomerAccountType {
    CURRENT;

    public String toUpperCamelCase() {
        return CaseUtils.toCamelCase(this.name(), true, ' ');
    }
}
