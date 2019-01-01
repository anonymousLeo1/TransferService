package io.revolut;

import io.revolut.domain.model.Account;
import io.revolut.domain.AccountRepository;
import java.math.BigDecimal;
import java.util.Random;
import java.math.BigInteger;
import java.util.stream.IntStream;
import java.lang.String;

public class TestAccountsPopulation {

    public static void populateAccounts(AccountRepository rep) {
        Random rand = new Random();

        IntStream.range(1,100).mapToObj(i -> {
                    String accountNumber = "IN" + Integer.toString(rand.nextInt(1000000));
                    Account x =  new Account(accountNumber);
                    x.credit("INR", new BigDecimal(rand.nextInt(1000000)));
                    return x;
                })
                .forEach(rep::save);
    }
}
