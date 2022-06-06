package com.av.finance.account.app.external;

import com.av.finance.account.app.dto.TxDetails;

import java.util.List;
import java.util.UUID;

public interface TransactionExternalService {

    List<TxDetails> getTransactionsForAccounts(List<UUID> accountIds);

    void createTransaction(TxDetails txDetails);
}
