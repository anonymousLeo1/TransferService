package io.revolut.domain;

import io.revolut.domain.model.Account;

import java.util.Collection;
import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findByAccountNumber(String accountNumber);

    Collection<Account> getAll();

    void delete(Account account);

    Account save(Account account);

}
