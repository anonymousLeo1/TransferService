package io.revolut.domain;

import io.revolut.domain.model.Account;
import java.math.BigDecimal;

public class TransferPacket {

	private Account transferFrom;
	private Account transferTo;
	private String currency;
	private BigDecimal amountToTransfer;

	public TransferPacket(Account sourceAccount, Account destinationAccount, String currency, BigDecimal amount) {

		this.transferTo = destinationAccount;
		this.transferFrom = sourceAccount;
		this.currency = currency;
		this.amountToTransfer = amount;
	}

	public String getCurrency(){
		return this.currency;
	}

	public Account getSourceAccount(){
		return this.transferFrom;
	}

	public Account getDestinationAccount(){
		return this.transferTo;
	}

	public BigDecimal getAmount(){
		return this.amountToTransfer;
	}
}