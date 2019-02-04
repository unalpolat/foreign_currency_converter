package com.currency.exception;

public class CurrencyException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer errorCode;
	private String errorDesc;
	
	public CurrencyException() {
	}

	public CurrencyException(String message) {
		super(message);
	}
	
	public CurrencyException(String message, Integer errorCode, String errorDesc) {
		super(message);
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

}
