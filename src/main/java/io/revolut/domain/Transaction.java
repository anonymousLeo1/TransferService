package io.revolut.domain;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

public class Transaction {

	@Getter
	private  UUID transactionId;
	@Getter
	private  String transactionMessage;

	public static final String SUCCESS = "Transfer Successful";
	public static final String FAIL = "Transfer NOT Successful";


	public Transaction(boolean result) {

		UUID uid = UUID.fromString("18400000-8cf0-11bd-b23e-10b96e4ef00d");
		
		this.transactionId = uid.randomUUID();

		this.transactionMessage = result ? SUCCESS : FAIL ;
	}
}