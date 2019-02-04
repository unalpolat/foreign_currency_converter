package com.currency.model;

import java.math.BigDecimal;

public class ExchangeRateResponse extends ResponseHeader{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal exchangeRate;

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

}
