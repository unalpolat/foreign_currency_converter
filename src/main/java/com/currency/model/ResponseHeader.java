package com.currency.model;

import java.io.Serializable;

public class ResponseHeader implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer resultCode;
	private String resultDesc;
	
	public void fillResponseHeader(Integer resultCode, String resultDesc) {
		this.resultCode = resultCode;
		this.resultDesc = resultDesc;
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
