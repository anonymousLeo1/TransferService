package io.revolut.domain.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.concurrent.locks.ReentrantLock;
import io.revolut.domain.*;


public class Account {

    private final String accountNumber;
    private Balance accountBalance;
    private final ReentrantLock lock = new ReentrantLock();

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.accountBalance = new Balance();
    }

    public Balance getAccountBalance() {

        return this.accountBalance;
    }

    public BigDecimal getAccountBalance(String currency) {

        return this.accountBalance.getBalance(currency);
    }

    public void credit(String currency, BigDecimal amount) {

        this.accountBalance.add(currency, amount);
    }

    public void debit(String currency, BigDecimal amount) {

        this.accountBalance.subtract(currency, amount);
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void lock() {
        this.lock.lock();
    }

    public void unlock() {
        this.lock.unlock();
    }
}
