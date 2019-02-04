package com.currency.constants;

public enum ResultCodes {
	
	OPERATION_SUCCESSFUL(200, "Operation Successful"),
	FATAL_ERROR(999, "Fatal Error"),
	MISSING_CHARACTER(998, "The currency pair must include 6 characters. [Required format: currencyPair=EURUSD, GBPUSD, TRYEUR, ...]"),
	NOT_FOUND_CURRENCY(997, "You have provided one or more invalid Currency Codes. [Required format: currencies=EUR, USD, GBP, ...]"),
	NULL_PARAMETER(996, "The parameter you entered is null: ");
	
	private Integer resultCode; 
	private String resultDesc;
	
	private ResultCodes(Integer resultCode, String resultDesc) {
		this.resultCode = resultCode;
		this.resultDesc = resultDesc;
	}
	
	public void setResultDescWithParameter(String parameterName) {
		this.resultDesc = this.resultDesc + parameterName;
	}

	public Integer getResultCode() {
		return resultCode;
	}
	
	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

}
