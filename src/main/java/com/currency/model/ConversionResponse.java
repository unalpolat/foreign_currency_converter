package com.currency.model;

import java.math.BigDecimal;

public class ConversionResponse extends ResponseHeader {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long transactionId;
	private BigDecimal targetAmount;

	public ConversionResponse() {
	}

	public ConversionResponse(Long transactionId, BigDecimal targetAmount) {
		this.transactionId = transactionId;
		this.targetAmount = targetAmount;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public BigDecimal getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(BigDecimal targetAmount) {
		this.targetAmount = targetAmount;
	}
}
