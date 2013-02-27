package com.model;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {

	private Client sourceAccount;
	private Client destinationAccount;
	private Date date;
	private BigDecimal sum;

	public Client getSourceAccount() {
		return sourceAccount;
	}

	public void setSourceAccount(Client sourceAccount) {
		this.sourceAccount = sourceAccount;
	}

	public Client getDestinationAccount() {
		return destinationAccount;
	}

	public void setDestinationAccount(Client destinationAccount) {
		this.destinationAccount = destinationAccount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getSum() {
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

}
