package io.revolut.domain;

import io.revolut.domain.model.*;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class CachedAccountRepository implements AccountRepository {

    private final Map<String, Account> accountStorage = new ConcurrentHashMap<>();

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return Optional.ofNullable(accountStorage.get(accountNumber));
    }

    @Override
    public Collection<Account> getAll() {
        return accountStorage.values();
    }

    @Override
    public Account save(Account account) {
        return accountStorage.put(account.getAccountNumber(), account);
    }

    @Override
    public void delete(Account account) {
        accountStorage.remove(account.getAccountNumber());
    }

    public void clear() {
        accountStorage.clear();
    }
}
