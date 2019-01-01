package io.revolut.domain;

import io.revolut.domain.model.Account;

import javax.inject.Singleton;
import java.math.BigDecimal;

import io.revolut.domain.Transaction;
import io.revolut.domain.TransferPacket;


@Singleton
public class TransactionService {

    public Transaction makeTransaction(TransferPacket packet) {
        Account source = packet.getSourceAccount();
        Account destination = packet.getDestinationAccount();

        source.lock();
        try {
            destination.lock();
            try {
                return processTransaction(packet);
            } finally {
                destination.unlock();
            }
        } finally {
            source.unlock();
        }

    }

    public Transaction processTransaction(TransferPacket packet) {

        if (packet.getSourceAccount().getAccountNumber().equals(packet.getDestinationAccount().getAccountNumber())){
                throw new IllegalArgumentException("Can't do Transaction b/w same accounts");
        }
        if(packet.getSourceAccount().getAccountBalance(packet.getCurrency()).compareTo(packet.getAmount()) < 0 ){
                throw new IllegalArgumentException("Account " + packet.getSourceAccount().getAccountNumber() + " has insufficient funds ");
        }

        packet.getSourceAccount().debit(packet.getCurrency(), packet.getAmount());
        packet.getDestinationAccount().credit(packet.getCurrency(), packet.getAmount());
        return new Transaction(true);

    }
}
