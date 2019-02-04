package com.currency.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ConversionRequest  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sourceCurrency;
	private String targetCurrency;
	private BigDecimal amount;
	
	public ConversionRequest() {
	}
	
	public ConversionRequest(String sourceCurrency, String targetCurrency, BigDecimal amount) {
		this.sourceCurrency = sourceCurrency;
		this.targetCurrency = targetCurrency;
		this.amount = amount;
	}
	
	public String getSourceCurrency() {
		return sourceCurrency;
	}
	public void setSourceCurrency(String sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}
	public String getTargetCurrency() {
		return targetCurrency;
	}
	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
