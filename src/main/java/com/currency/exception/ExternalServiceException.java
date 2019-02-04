package com.currency.exception;

public class ExternalServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer errorCode;
	private String errorDesc;

	public ExternalServiceException() {
	}

	public ExternalServiceException(String message) {
		super(message);
	}

	public ExternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public ExternalServiceException(String message, Integer errorCode, String errorDesc) {
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
