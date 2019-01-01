package io.revolut.api;

import io.revolut.domain.AccountRepository;
import io.revolut.api.TransferService;
import io.revolut.domain.Transaction;
import io.revolut.domain.model.Account;
import io.revolut.api.RequestPacket;

import lombok.RequiredArgsConstructor;
import org.jooby.Err;
import org.jooby.Result;
import org.jooby.Results;
import org.jooby.mvc.*;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.Collection;
import java.math.BigDecimal;



@Path("/accounts")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AccountApiController {

    private final TransferService transferService;
    private final AccountRepository accountRepository;

    @GET
    public Collection<Account> getAll() {
        return accountRepository.getAll();
    }

    @Path("/{id}")
    @GET
    public Account getOne(String id) {
        return accountRepository.findByAccountNumber(id)
                .orElseThrow(() -> new Err(404));
    }

    @Path("/{id}/{currency}")
    @GET
    public BigDecimal getCurrencyBalance(String id, String currency) {
        Account acc =  accountRepository.findByAccountNumber(id)
                .orElseThrow(() -> new Err(404));
        return acc.getAccountBalance(currency);
    }

    @Path("/transfer")
    @POST
    public Result makeTransfer(@Body RequestPacket request) {
        Transaction t = transferService.makeTransfer( request.getSource(), 
                                                      request.getDestination(), 
                                                      request.getCurrency(),
                                                      request.getAmount());
        return Results.with(t);
    }

}
