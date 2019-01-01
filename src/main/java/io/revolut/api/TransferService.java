package io.revolut.api;

import io.revolut.domain.model.Account;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.math.BigInteger;

import io.revolut.domain.CachedAccountRepository;
import io.revolut.domain.Transaction;
import io.revolut.domain.TransferPacket;
import io.revolut.domain.TransactionService;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TransferService {

    private final CachedAccountRepository accountRepository;
    private final TransactionService transactionService;

    public Transaction makeTransfer(String source, String destination, String currency, BigDecimal amount) {
        Account sourceAccount = getAccountByNumber(source);
        Account destinationAccount = getAccountByNumber(destination);

        TransferPacket packet = new TransferPacket(sourceAccount, destinationAccount, currency, amount);

        return transactionService.makeTransaction(packet);
    }

    private Account getAccountByNumber(String accountNum) {
        return accountRepository.findByAccountNumber(accountNum)
                .orElseThrow(() -> new IllegalArgumentException("Account " + accountNum + " not found!"));
    }
}
