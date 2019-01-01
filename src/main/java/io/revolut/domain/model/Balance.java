package io.revolut.domain.model;

import java.math.BigDecimal;
import java.util.*;
import io.revolut.domain.model.Currency;



public class Balance {

	private HashMap<String, BigDecimal> allBalances;

	public Balance() {

		this.allBalances = new HashMap();

		for (Currency currency: Currency.values()) {
			allBalances.put(currency.toString(), BigDecimal.ZERO);
		}
	}

	public BigDecimal getBalance(String currency) {
		return this.allBalances.get(currency);
	}

	public HashMap<String,BigDecimal> getBalance() {
		return this.allBalances;
	}

	public boolean contains(String currency) {
		return this.allBalances.containsKey(currency);
	}

	public void add(String currency, BigDecimal amount) {
		if (!this.contains(currency)) {
			throw new IllegalArgumentException("Account doesn't support this currency");
		}
		this.allBalances.put(currency,this.allBalances.get(currency).add(amount));

	}

	public void subtract(String currency, BigDecimal amount) {
		if (!this.contains(currency)) {
			throw new IllegalArgumentException("Account doesn't support this currency");
		}
		this.allBalances.put(currency,this.allBalances.get(currency).subtract(amount));
	}

}


